package com.jwt.seguridad.spring.springbootjwt.services.impl;

import com.jwt.seguridad.spring.springbootjwt.entity.TagEntity;
import com.jwt.seguridad.spring.springbootjwt.repository.TagRepository;
import com.jwt.seguridad.spring.springbootjwt.services.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImp implements ITagService {

    private final TagRepository tagRepository;

    /**
     * @return
     */
    @Override
    public List<TagEntity> findAll() {
        return tagRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<TagEntity> findById(Long id) {
        return tagRepository.findById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<TagEntity> findTagsByTutorialsId(Long id) {
        return tagRepository.findTagsByTutorialsId(id);
    }

    /**
     * @param tag
     * @return
     */
    @Override
    public TagEntity save(TagEntity tag) {
        return tagRepository.save(tag);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public void deleteById(Long id) {
         tagRepository.deleteById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean existsById(Long id) {
        return tagRepository.existsById(id);
    }


}
