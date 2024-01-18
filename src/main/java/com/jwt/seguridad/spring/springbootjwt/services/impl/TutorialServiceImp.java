package com.jwt.seguridad.spring.springbootjwt.services.impl;

import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import com.jwt.seguridad.spring.springbootjwt.repository.TutorialRepository;
import com.jwt.seguridad.spring.springbootjwt.services.ITutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TutorialServiceImp implements ITutorialService {

    private final TutorialRepository tutorialRepository;

    /**
     * @param tagId
     * @return
     */
    @Override
    public List<TutorialEntity> findTutorialsByTagsId(Long tagId) {
        return tutorialRepository.findTutorialsByTagsId(tagId);
    }

    /**
     * @param title
     * @return
     */
    @Override
    public List<TutorialEntity> findByTitleContaining(String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    /**
     * @param b
     * @return
     */
    @Override
    public List<TutorialEntity> findByPublished(boolean b) {
        return tutorialRepository.findByPublished(b);
    }

    /**
     * @return
     */
    @Override
    public List<TutorialEntity> findAll() {
        return tutorialRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<TutorialEntity> findById(Long id) {
        return tutorialRepository.findById(id);
    }

    /**
     * @param tutorial
     * @return
     */
    @Override
    public TutorialEntity save(TutorialEntity tutorial) {
        return tutorialRepository.save(tutorial);
    }

    /**
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        tutorialRepository.deleteById(id);
    }

    /**
     *
     */
    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<TutorialEntity> findAll(Pageable pageable) {
        return tutorialRepository.findAll(pageable);
    }
}
