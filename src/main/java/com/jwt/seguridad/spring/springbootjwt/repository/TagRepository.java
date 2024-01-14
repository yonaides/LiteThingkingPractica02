package com.jwt.seguridad.spring.springbootjwt.repository;

import com.jwt.seguridad.spring.springbootjwt.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByTutorialsId(Long tutorialId);
}
