package com.acazia.music.controllers;

import com.acazia.music.base.BaseController;
import com.acazia.music.base.BaseResponseDto;
import com.acazia.music.base.CommonConstant;
import com.acazia.music.dto.produce.BaseListProduceDto;
import com.acazia.music.dto.produce.SongProduceDto;
import com.acazia.music.models.Song;
import com.acazia.music.payload.response.ResponseMessage;
import com.acazia.music.repository.SongRepository;
import com.acazia.music.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/")
public class SongController extends BaseController {

    @Autowired
    private SongService songService;
    Song song = new Song();

    @Autowired
    private SongRepository songRepository;

    @GetMapping(value = "/songs")
    public ResponseEntity<BaseResponseDto> getAllSongs(
            @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
            @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(defaultValue = CommonConstant.EMPTY) String name
    ) {
        Pageable pageable = getPageable(page, size, sort);
        Page<SongProduceDto> songProduceDtoPage = songService.findAllSong(name, pageable);

        return success(BaseListProduceDto.build(songProduceDtoPage), "get data successful.");
    }

    @GetMapping(value = "/songs/{id}")
    public ResponseEntity<BaseResponseDto> getSongById(@PathVariable(value = "id") Long id,
                                                       @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
                                                       @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
                                                       @RequestParam(required = false) String[] sort) {
        Pageable pageable = getPageable(page, size, sort);
        Page<SongProduceDto> songProduceDtoPage = songService.findSongById(id, pageable);

        return success(BaseListProduceDto.build(songProduceDtoPage), "get data successful.");
    }

    @PostMapping("/songs/new")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void comment(@RequestBody Song song) {
        this.song = song;
    }

    @PostMapping(value = "/songs/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        File convertFile = new File("D:\\My music\\" + file.getOriginalFilename());
        com.google.api.services.drive.model.File file2 = songService.upLoadFile(convertFile.getName(), convertFile.getAbsolutePath(), "audio/mp3/m4a/wan/flac");
        if (file2.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Could not uploaded because it was missing"));
        } else {
            song.setIdGoogle(file2.getId());
            song.setUrl(file2.getWebContentLink());
            songRepository.save(song);
            return ResponseEntity.ok().body(new ResponseMessage("Upload successful"));
        }
    }

}