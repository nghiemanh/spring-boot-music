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

    @Column(name = "name")
    private String name;

    @Column(name = "singer")
    private String singer;

    private String url;

    @Column(name ="avatar")
    private String avatar;

    @Column(name = "plays")
    private Long plays;

    @Column(name = "type")
    private String type;

    @Column(name = "playlist_id")
    private Long playListId;

    @Lob
    private byte[] data;

    public Song(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
