package com.acazia.music.mapper;

import com.acazia.music.dto.produce.SongProduceDto;
import com.acazia.music.models.Song;
import com.acazia.music.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SongMapper {

    public Optional<SongProduceDto> toSongProduceDto(Song song){
        if(Objects.isNull(song)){
            return Optional.empty();
        }
        return Optional.of(SongProduceDto.builder()
                .id(song.getId())
                .name(song.getName())
                .url(song.getUrl())
                .plays(song.getPlays())
                .type(song.getType())
                .createDate(DateUtils.convertToUtilDate(song.getCreateDate()))
                .updateDate(DateUtils.convertToUtilDate(song.getUpdateDate()))
                .createBy(song.getCreatedBy())
                .updateBy(song.getUpdatedBy())
                .build());
    }
}
