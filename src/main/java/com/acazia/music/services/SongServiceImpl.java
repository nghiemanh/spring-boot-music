package com.acazia.music.services;

import com.acazia.music.dto.produce.SongProduceDto;
import com.acazia.music.mapper.SongMapper;
import com.acazia.music.models.Song;
import com.acazia.music.repository.SongRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;


@Service
public class SongServiceImpl {

    @Autowired
    private final SongMapper songMapper;
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private DriveService driveService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SongServiceImpl.class);

    @Value("${service_account_email}")
    private String serviceAccountEmail;

    @Value("${application_name}")
    private String applicationName;

    @Value("${service_account_key}")
    private String serviceAccountKey;

    @Value("${folder_id}")
    private String folderID;

    public SongServiceImpl(SongMapper songMapper) {
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

    public Page<SongProduceDto> findAllSong(Pageable pageable){
        Page<Song> songs = songRepository.findAll(pageable);
        if (songs.isEmpty()){
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return songs.map(song -> songMapper.toSongProduceDto(song).orElse(null));
    }


}

