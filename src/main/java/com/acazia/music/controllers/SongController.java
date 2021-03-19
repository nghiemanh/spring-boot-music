package com.acazia.music.controllers;

import com.acazia.music.models.Song;
import com.acazia.music.payload.response.ResponseMessage;
import com.acazia.music.payload.response.ResponseSong;
import com.acazia.music.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class SongController {

    @Autowired
    private SongService songService;
    @PostMapping("/upload")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            songService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/songs")
    public ResponseEntity<List<ResponseSong>> getListSongs(){
        List<ResponseSong> song = songService.getAllSong().map(dbSong -> {
            String fileDownloadUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/songs/")
                    .path(String.valueOf((dbSong.getId())))
                    .toUriString();

            return new ResponseSong(
                    dbSong.getName(),
                    fileDownloadUrl,
                    dbSong.getType(),
                    dbSong.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(song);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<byte[]> getSong(@PathVariable Long id){
        Song song = songService.getSongById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + song.getName() + "\"")
                .body(song.getData());
    }
}
