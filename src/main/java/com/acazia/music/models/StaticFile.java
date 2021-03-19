package com.acazia.music.models;

import com.acazia.music.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "static_file")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StaticFile extends BaseEntity {
    @Column(name = "file_name")
    @JsonProperty("file_name")
    private String fileName;

    @Column(name = "file_size")
    @JsonProperty("file_size")
    private Long fileSize;

    @Column(name = "file_path")
    @JsonProperty("file_path")
    private String filePath;

    @Column(name = "upload_type")
    @JsonProperty("upload_type")
    private Integer uploadType;
}