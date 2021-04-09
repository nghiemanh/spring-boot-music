package com.acazia.music.mapper;

import com.acazia.music.dto.produce.PlayListProduceDto;
import com.acazia.music.models.PlayList;
import com.acazia.music.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class PlayListMapper {

    public Optional<PlayListProduceDto> toPlayListProduceDto(PlayList playList) {
        if (Objects.isNull(playList)) {
            return Optional.empty();
        }
        return Optional.of(PlayListProduceDto.builder()
                .id(playList.getId())
                .name(playList.getName())
                .createDate(DateUtils.convertToUtilDate(playList.getCreateDate()))
                .updateDate(DateUtils.convertToUtilDate(playList.getUpdateDate()))
                .createBy(playList.getCreatedBy())
                .updateBy(playList.getUpdatedBy())
                .song(playList.getSongs())
                .build());
    }
}
