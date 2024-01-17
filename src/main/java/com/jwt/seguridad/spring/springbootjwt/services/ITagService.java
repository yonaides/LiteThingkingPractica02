package com.jwt.seguridad.spring.springbootjwt.services;

import com.jwt.seguridad.spring.springbootjwt.entity.TagEntity;

import java.util.List;
import java.util.Optional;

public interface ITagService {

    List<TagEntity> findAll();
    Optional<TagEntity> findById(Long id);
    List<TagEntity> findTagsByTutorialsId(Long id);

    TagEntity save(TagEntity tag);

    void deleteById(Long id);

    Boolean existsById(Long id);

}
