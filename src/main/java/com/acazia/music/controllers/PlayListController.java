package com.acazia.music.controllers;


import com.acazia.music.base.BaseController;
import com.acazia.music.base.BaseResponseDto;
import com.acazia.music.base.CommonConstant;
import com.acazia.music.dto.produce.BaseListProduceDto;
import com.acazia.music.dto.produce.PlayListProduceDto;
import com.acazia.music.exception.ResourceNotFoundException;
import com.acazia.music.models.PlayList;
import com.acazia.music.models.Song;
import com.acazia.music.repository.PlayListRepository;
import com.acazia.music.repository.SongRepository;
import com.acazia.music.repository.UserRepository;
import com.acazia.music.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PlayListController extends BaseController {

    @Autowired
    private PlayListService playListService;
    @Autowired
    private PlayListRepository playListRepository;
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/playlist")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<BaseResponseDto> getAllPlayList(
            @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
            @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
            @RequestParam(required = false) String[] sort,
            @RequestParam(defaultValue = CommonConstant.EMPTY) String name
    ) {
        Pageable pageable = getPageable(page, size, sort);
        Page<PlayListProduceDto> playListProduceDtos = playListService.findAllPlayList(name, pageable);

        return success(BaseListProduceDto.build(playListProduceDtos), "get data successful.");
    }

    @GetMapping("/playlist/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<BaseResponseDto> getPlayListById(@PathVariable(value = "id") Long id,
                                                           @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
                                                           @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
                                                           @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = getPageable(page, size, sort);
        Page<PlayListProduceDto> playListProduceDtos = playListService.findPlayListById(id, pageable);

        return success(BaseListProduceDto.build(playListProduceDtos), "get data successful.");
    }

    @PostMapping("/playlist/{playlistId}/song/{songId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<PlayList> addSongToPlayList(
            @PathVariable(value = "playlistId") Long playlistId,
            @PathVariable(value = "songId") Long songId) throws ResourceNotFoundException {
        Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found for this id :: " + songId));
        PlayList playList = playListRepository.findById(playlistId).orElseThrow(() -> new ResourceNotFoundException("Playlist not found for this id :: " + playlistId));
        song.setId(songId);
        playList.setId(playlistId);
        playList.getSongs().add(song);
        final PlayList addPlayList = playListRepository.save(playList);
        return ResponseEntity.ok(addPlayList);
    }

    @PostMapping("/{userId}/playlist/new")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public PlayList makeListByUser(@PathVariable(value = "userId") Long userId,
                                   @RequestBody PlayList newPlayList) throws ResourceNotFoundException {
        return userRepository.findById(userId).map(user -> {
            newPlayList.setUser(user);
            return playListRepository.save(newPlayList);
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @GetMapping("{userId}/playlist")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<BaseResponseDto> getPlayListByUserId(@PathVariable(value = "userId") Long userId,
                                                               @RequestParam(defaultValue = CommonConstant.PageableConstant.PAGE_START) Integer page,
                                                               @RequestParam(defaultValue = CommonConstant.PageableConstant.ITEM_PER_PAGE) Integer size,
                                                               @RequestParam(required = false) String[] sort
    ) {
        Pageable pageable = getPageable(page, size, sort);
        Page<PlayListProduceDto> playListProduceDtos = playListService.findPlayListByUserId(userId, pageable);

        return success(BaseListProduceDto.build(playListProduceDtos), "get data successful.");
    }

    @DeleteMapping("user/{userId}/playList/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<PlayList> deletePlayList(@PathVariable(value = "id") Long id,
                                                   @PathVariable Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).map(user -> {
            Optional<PlayList> playList = playListRepository.findById(id);
            return playList.map(playList1 -> {
                playListRepository.deleteById(id);
                return new ResponseEntity<>(playList1, HttpStatus.OK);
            }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }
}

