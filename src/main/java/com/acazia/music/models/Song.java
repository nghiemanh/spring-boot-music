package com.acazia.music.models;

import com.acazia.music.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "single")
    private String single;

    @Column(name = "url")
    private String url;

    @Column(name = "plays")
    private Long plays;

    @Column(name = "category")
    private String category;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy ="songs")
    private Set<PlayList> playLists = new HashSet<>();
}
