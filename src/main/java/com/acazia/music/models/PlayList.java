package com.acazia.music.models;

import com.acazia.music.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "playlist")
public class PlayList extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinTable(name = "song_plist",
            joinColumns =
                    {@JoinColumn(name = "playlist_id")
                    },
            inverseJoinColumns =
                    {@JoinColumn(name = "song_id")
                    })
    private Set<Song> songs = new HashSet<>();

    @JsonIgnore
    @ManyToOne( cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;
}
