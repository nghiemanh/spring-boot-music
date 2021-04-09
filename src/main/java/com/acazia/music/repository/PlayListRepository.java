package com.acazia.music.repository;

import com.acazia.music.models.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {

    Page<PlayList> findAllByNameContaining(String name, Pageable pageable);

    Page<PlayList> findByIdIsLike(Long id, Pageable pageable);

    Page<PlayList> findAllByUser_Id(Long userId, Pageable pageable);
}
