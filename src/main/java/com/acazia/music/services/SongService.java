package com.acazia.music.services;

import com.acazia.music.models.Song;
import com.acazia.music.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    public Song store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Song song = new Song(fileName, file.getContentType(), file.getBytes());

        return songRepository.save(song);
    }

    public Song getSongById(Long id){
        return songRepository.findById(id).get();
    }

    public Stream<Song> getAllSong(){
        return songRepository.findAll().stream();
    }
}
