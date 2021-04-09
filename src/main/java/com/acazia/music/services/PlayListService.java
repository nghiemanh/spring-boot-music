package com.acazia.music.services;

import com.acazia.music.dto.produce.PlayListProduceDto;
import com.acazia.music.mapper.PlayListMapper;
import com.acazia.music.models.PlayList;
import com.acazia.music.repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private PlayListMapper playListMapper;

    public Page<PlayListProduceDto> findAllPlayList(String name, Pageable pageable) {
        Page<PlayList> playLists = playListRepository.findAllByNameContaining(name, pageable);
        if (playLists.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return playLists.map(playList -> playListMapper.toPlayListProduceDto(playList).orElse(null));
    }

    public Page<PlayListProduceDto> findPlayListById(Long id, Pageable pageable) {
        Page<PlayList> playLists = playListRepository.findByIdIsLike(id, pageable);
        if (playLists.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(),pageable, 0);
        }
        return playLists.map(playList -> playListMapper.toPlayListProduceDto(playList).orElse(null));
    }

    public Page<PlayListProduceDto> findPlayListByUserId(Long userId, Pageable pageable){
        Page<PlayList> playLists = playListRepository.findAllByUser_Id(userId, pageable);
        if (playLists.isEmpty()){
            return new PageImpl<>(Collections.emptyList(),pageable, 0);
        }
        return playLists.map(playList -> playListMapper.toPlayListProduceDto(playList).orElse(null));
    }
}

