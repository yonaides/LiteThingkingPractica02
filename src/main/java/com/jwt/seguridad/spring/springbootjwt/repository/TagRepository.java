package com.jwt.seguridad.spring.springbootjwt.repository;

import com.jwt.seguridad.spring.springbootjwt.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findTagsByTutorialsId(Long tutorialId);
}
