package com.jwt.seguridad.spring.springbootjwt.repository;

import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<TutorialEntity, Long> {
    List<TutorialEntity> findTutorialsByTagsId(Long tagId);

    List<TutorialEntity> findByTitleContaining(String title);

    List<TutorialEntity> findByPublished(boolean b);
}
