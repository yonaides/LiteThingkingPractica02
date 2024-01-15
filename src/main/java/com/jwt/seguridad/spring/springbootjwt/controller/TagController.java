package com.jwt.seguridad.spring.springbootjwt.controller;

import com.jwt.seguridad.spring.springbootjwt.entity.Tag;
import com.jwt.seguridad.spring.springbootjwt.entity.Tutorial;
import com.jwt.seguridad.spring.springbootjwt.exception.ResourceNotFoundException;
import com.jwt.seguridad.spring.springbootjwt.exception.RuntimeExceptionError;
import com.jwt.seguridad.spring.springbootjwt.exception.TutorialNotFoundException;
import com.jwt.seguridad.spring.springbootjwt.repository.TagRepository;
import com.jwt.seguridad.spring.springbootjwt.repository.TutorialRepository;
import com.jwt.seguridad.spring.springbootjwt.response.CustomResponseHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class TagController {

    private final TutorialRepository tutorialRepository;
    private final TagRepository tagRepository;

    /**
     *
     * @param tutorialRepository
     * @param tagRepository
     */
    @Autowired
    public TagController(TutorialRepository tutorialRepository, TagRepository tagRepository) {
        this.tutorialRepository = tutorialRepository;
        this.tagRepository = tagRepository;
    }

    /**
     *
     * @return
     */
    @GetMapping("/tags")
    public ResponseEntity<Object> getAllTags() {
        List<Tag> tagList = new ArrayList<Tag>();

        tagRepository.findAll().forEach(tagList::add);

        if (tagList.isEmpty()) {
            return CustomResponseHandler.generateResponse("Listado de tags vacio",HttpStatus.NO_CONTENT, tagList) ;
        }
        return CustomResponseHandler.generateResponse("listado de tags ",HttpStatus.OK, tagList) ;

    }

    /**
     *
     * @param tutorialId
     * @return
     */
    @GetMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<Object> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId) {
        if (!tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        List<Tag> tagsList = tagRepository.findTagsByTutorialsId(tutorialId);
        return CustomResponseHandler.generateResponse("Listado de tags",HttpStatus.OK, tagsList) ;

    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/tags/{id}")
    public ResponseEntity<Object> getTagsById(@PathVariable(value = "id") Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

        return CustomResponseHandler.generateResponse("Tag encontrado",HttpStatus.OK, tag) ;
    }

    /**
     *
     * @param tagId
     * @return
     */
    @GetMapping("/tags/{tagId}/tutorials")
    public ResponseEntity<Object> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new ResourceNotFoundException("Tag no encontrado con el id = " + tagId);
        }

        List<Tutorial> tutorials = tutorialRepository.findTutorialsByTagsId(tagId);
        return CustomResponseHandler.generateResponse("Tag encontrado con tutoriales",HttpStatus.OK, tutorials) ;

    }

    /**
     *
     * @param tutorialId
     * @param tagRequest
     * @return
     */
    @PostMapping("/tutorials/{tutorialId}/tags")
    public ResponseEntity<Object> addTag(@PathVariable(value = "tutorialId") Long tutorialId,
                                         @RequestBody Tag tagRequest) {
        try {
            Tag tag = tutorialRepository.findById(tutorialId).map(tutorial -> {
                long tagId = tagRequest.getId();

                // tag is existed
                if (tagId != 0L) {
                    Tag _tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con el id = " + tagId));
                    tutorial.addTag(_tag);
                    tutorialRepository.save(tutorial);
                    return _tag;
                }

                // add and create new Tag
                tutorial.addTag(tagRequest);
                return tagRepository.save(tagRequest);
            }).orElseThrow(() -> new TutorialNotFoundException("Tutorial no encontrado = " + tutorialId));

            return CustomResponseHandler.generateResponse("Tags Creado",HttpStatus.OK, tag) ;

        }catch(Exception ex){
            throw new RuntimeExceptionError("Error en el servidor ".concat(ex.getMessage()));
        }

    }

    /**
     *
     * @param id
     * @param tagRequest
     * @return
     */
    @PutMapping("/tags/{id}")
    public ResponseEntity<Object> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Tag " + id + "not found"));

        tag.setName(tagRequest.getName());

        return CustomResponseHandler.generateResponse("Tag editado",HttpStatus.OK, tagRepository.save(tag)) ;

    }

    /**
     *
     * @param tutorialId
     * @param tagId
     * @return
     */
    @DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
    public ResponseEntity<Object> deleteTagFromTutorial(@PathVariable(value = "tutorialId") Long tutorialId,
                                                        @PathVariable(value = "tagId") Long tagId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new ResourceNotFoundException("Id del Tutorial no encontrado, ID = " + tutorialId));

        tutorial.removeTag(tagId);
        tutorialRepository.save(tutorial);

        return CustomResponseHandler.generateResponse("Tag del tutorial eliminado ",HttpStatus.NO_CONTENT, tutorial) ;
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") long id) {
        tagRepository.deleteById(id);

        return CustomResponseHandler.generateResponse("Tag eliminado",HttpStatus.NO_CONTENT, id) ;

    }

}
