package com.acazia.music.repository;

import com.acazia.music.models.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SongRepository extends JpaRepository <Song, Long> {

    Page<Song> findAllByNameContaining(String name, Pageable pageable);

    Page<Song> findAllByCreatedByIsLike(String createBy, Pageable pageable);

    Page<Song> findByIdIsLike(Long id,Pageable pageable);

}
