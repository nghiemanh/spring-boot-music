package com.acazia.music.repository;

import com.acazia.music.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SongRepository extends JpaRepository <Song, Long> {
}
