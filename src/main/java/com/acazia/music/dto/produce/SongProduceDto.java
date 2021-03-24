package com.acazia.music.dto.produce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class SongProduceDto implements Serializable {

    private Long id;

    private String name;

    private String url;

    private Long plays;

    private String type;

    private Date createDate;

    private Date updateDate;

    private String createBy;

    private String updateBy;

}
