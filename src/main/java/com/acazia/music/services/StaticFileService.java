package com.acazia.music.services;

import com.acazia.music.enums.FileUploadType;
import com.acazia.music.exception.BadRequestException;
import com.acazia.music.models.StaticFile;
import com.acazia.music.utils.EnumUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class StaticFileService {
    @Value("${resource-path}")
    String staticFilePath;

    @Value("${file.max-avatar-size-in-mb}")
    int maxAvatarSize;

    public StaticFile storeFile(MultipartFile file, Integer uploadType, Long targetId) throws IOException {
        if (FileUploadType.SONG.getCode().equals(uploadType)
                || FileUploadType.AVATAR.getCode().equals(uploadType)) {
            long fileSize = file.getSize();
            if (maxAvatarSize * 1024 * 1024l <= fileSize) {
                throw new BadRequestException("File too large" + fileSize + "byte");
            }
        }

        Path filePath = saveFileToLocal(file, uploadType, targetId);
        return StaticFile.builder()
                .fileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .filePath(filePath.toString())
                .uploadType(uploadType)
                .build();
    }

    private Path saveFileToLocal(MultipartFile file, Integer uploadType, Long targetId) throws IOException {
        Path filePath = getFilePath(uploadType, file.getOriginalFilename(), targetId);

        Files.createDirectories(Paths.get(staticFilePath).resolve(filePath.getParent()));
        writeFile(file, Paths.get(staticFilePath).resolve(filePath));
        return filePath;
    }

    private Path getFilePath(Integer uploadTypeCode, String fileName, Long targetId) {
        Optional<FileUploadType> optionalFileUploadType = EnumUtils.getEnumByCode(FileUploadType.class, uploadTypeCode);
        Path filePath;
        if (optionalFileUploadType.isPresent()) {
            FileUploadType fileUploadType = optionalFileUploadType.get();
            filePath = Paths.get(fileUploadType.getDescription(), String.valueOf(targetId));
        } else {
            filePath = Paths.get(FileUploadType.OTHER.getDescription());
        }
        return filePath.resolve(fileName);
    }

    private void writeFile(MultipartFile sourceFile, Path output) throws IOException {
        sourceFile.transferTo(output);
    }
}
