package com.acazia.music.dto.produce;

import com.acazia.music.models.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PlayListProduceDto implements Serializable {

    private Long id;

    private String name;

    private Date createDate;

    private Date updateDate;

    private String createBy;

    private String updateBy;

    private Set<Song> song;
}
