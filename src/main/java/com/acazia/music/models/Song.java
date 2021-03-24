package com.acazia.music.models;
import com.acazia.music.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "song")
public class Song extends BaseEntity {

    @Column(name = "idgoogle")
    private String idGoogle;

    @Column(name = "name")
    private String name;

    @Column(name = "singer")
    private String singer;

    @Column(name = "url")
    private String url;

    @Column(name = "plays")
    private Long plays;

    @Column(name = "type")
    private String type;

}
