package com.jwt.seguridad.spring.springbootjwt.repository;

import com.jwt.seguridad.spring.springbootjwt.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findTutorialsByTagsId(Long tagId);

    List<Tutorial> findByTitleContaining(String title);

    List<Tutorial> findByPublished(boolean b);
}
