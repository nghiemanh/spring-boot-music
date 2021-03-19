package com.acazia.music.base.interfaces;

import com.acazia.music.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IBaseService<E extends BaseEntity> {

    E save(E entity);

    E saveAndRefresh(E entity);

    E findById(Long id);

    Page<E> findAll(Pageable page);

    Page<E> findAll(Specification<E> spec, Pageable page);

    List<E> findAllById(List<Long> ids);

    List<E> findAll();

    void deleteById(Long id);

    boolean existById(Long id);
}