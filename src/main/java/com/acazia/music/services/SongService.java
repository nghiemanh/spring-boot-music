package com.acazia.music.services;

import com.acazia.music.dto.produce.SongProduceDto;
import com.acazia.music.mapper.SongMapper;
import com.acazia.music.models.Song;
import com.acazia.music.repository.SongRepository;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class SongService {

    @Autowired
    private final SongMapper songMapper;
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private DriveService driveService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SongService.class);

    @Value("${service_account_email}")
    private String serviceAccountEmail;

    @Value("${application_name}")
    private String applicationName;

    @Value("${service_account_key}")
    private String serviceAccountKey;

    @Value("${folder_id}")
    private String folderID;

    public SongService(SongMapper songMapper) {
        this.songMapper = songMapper;
    }

    public File upLoadFile(String fileName, String filePath, String mimeType) {
        File file = new File();
        try {
            java.io.File fileUpload = new java.io.File(filePath);
            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setMimeType(mimeType);
            fileMetadata.setName(fileName);
            fileMetadata.setParents(Collections.singletonList(folderID));
            FileContent fileContent = new FileContent(mimeType, fileUpload);
            file = driveService.GetDriveService().files().create(fileMetadata, fileContent)
                    .setFields("id,webContentLink,webViewLink,name").execute();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return file;
    }

    public Page<SongProduceDto> findAllSong(String name, Pageable pageable) {
        Page<Song> songs = songRepository.findAllByNameContaining(name, pageable);
        if (songs.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return songs.map(song -> songMapper.toSongProduceDto(song).orElse(null));
    }

    public Page<SongProduceDto> findSongById(Long id, Pageable pageable) {
        Page<Song> songs = songRepository.findByIdIsLike(id, pageable);
        if (songs.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }
        return songs.map(song -> songMapper.toSongProduceDto(song).orElse(null));
    }

    public Page<SongProduceDto> findSongByUserName(String name, Pageable pageable) {
        Page<Song> song = songRepository.findAllByCreatedByIsLike(name, pageable);
        if (song.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return song.map(songs -> songMapper.toSongProduceDto(songs).orElse(null));
    }

}

