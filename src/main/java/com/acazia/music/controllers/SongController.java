package com.acazia.music.controllers;

import com.acazia.music.base.BaseController;
import com.acazia.music.base.BaseResponseDto;
import com.acazia.music.base.CommonConstant;
import com.acazia.music.dto.produce.BaseListProduceDto;
import com.acazia.music.dto.produce.SongProduceDto;
import com.acazia.music.models.Song;
import com.acazia.music.payload.response.ResponseMessage;
import com.acazia.music.repository.SongRepository;
import com.acazia.music.services.SongServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("api/songs")
public class SongController extends BaseController {

    @Autowired
    private SongServiceImpl songService;

    @Autowired
    private SongRepository songRepository;

    @GetMapping
    public ResponseEntity<BaseResponseDto> getAllSongs(
            @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
            @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
            @RequestParam(required = false) String[] sort
    ){
        Pageable pageable = getPageable(page, size, sort);
        Page<SongProduceDto> sponsorProduceDtoPage = songService.findAllSong(pageable);

        return success(BaseListProduceDto.build(sponsorProduceDtoPage), "get data successful.");
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        Song song = new Song();
        File convertFile = new File("D:\\My music\\" + file.getOriginalFilename());
        com.google.api.services.drive.model.File file2 = songService.upLoadFile(convertFile.getName(), convertFile.getAbsolutePath(), "/mp3/m4a/wan/flac");
        if(file2.isEmpty()){
            return ResponseEntity.badRequest().body(new ResponseMessage("Could not uploaded because it was missing"));
        }else {
            song.setIdGoogle(file2.getId());
            song.setName(file2.getName());
            song.setUrl(file2.getWebViewLink());
            song.setType(file2.getMimeType());
            songRepository.save(song);
            return ResponseEntity.badRequest().body(new ResponseMessage("Upload successful"));
        }
    }
}