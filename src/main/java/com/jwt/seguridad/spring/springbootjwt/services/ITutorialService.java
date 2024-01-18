package com.jwt.seguridad.spring.springbootjwt.services;

import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITutorialService {

    List<TutorialEntity> findTutorialsByTagsId(Long tagId);

    List<TutorialEntity> findByTitleContaining(String title);

    List<TutorialEntity> findByPublished(boolean b);

    List<TutorialEntity> findAll();

    Optional<TutorialEntity> findById(Long id);

    TutorialEntity save(TutorialEntity tutorial);

    void deleteById(Long id);

    void deleteAll();

    Page<TutorialEntity> findAll(Pageable pageable);
}
