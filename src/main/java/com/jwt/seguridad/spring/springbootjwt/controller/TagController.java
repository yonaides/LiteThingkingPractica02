package com.jwt.seguridad.spring.springbootjwt.controller;

import com.jwt.seguridad.spring.springbootjwt.dto.TagDto;
import com.jwt.seguridad.spring.springbootjwt.entity.TagEntity;
import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import com.jwt.seguridad.spring.springbootjwt.exception.ResourceNotFoundException;
import com.jwt.seguridad.spring.springbootjwt.exception.RuntimeExceptionError;
import com.jwt.seguridad.spring.springbootjwt.exception.TutorialNotFoundException;
import com.jwt.seguridad.spring.springbootjwt.mapper.mappers.Mapper;
import com.jwt.seguridad.spring.springbootjwt.response.CustomResponseHandler;

import com.jwt.seguridad.spring.springbootjwt.services.ITagService;
import com.jwt.seguridad.spring.springbootjwt.services.ITutorialService;
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

    private final ITutorialService tutorialService;
    private final ITagService tagService;
    private final Mapper<TagEntity, TagDto> tagMapper;

    /**
     *
     * @param tutorialService
     * @param tagService
     * @param tagMapper
     */
    @Autowired
    public TagController(ITutorialService tutorialService, ITagService tagService, Mapper<TagEntity, TagDto> tagMapper) {
        this.tutorialService = tutorialService;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    /**
     *
     * @return
     */
    @GetMapping("/tags")
    public ResponseEntity<Object> getAllTags() {
        List<TagEntity> tagList = new ArrayList<TagEntity>();

        tagService.findAll().forEach(tagList::add);

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
        if (Boolean.FALSE.equals(tagService.existsById(tutorialId))) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        List<TagEntity> tagsList = tagService.findTagsByTutorialsId(tutorialId);
        return CustomResponseHandler.generateResponse("Listado de tags",HttpStatus.OK, tagsList) ;

    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/tags/{id}")
    public ResponseEntity<Object> getTagsById(@PathVariable(value = "id") Long id) {
        TagEntity tag = tagService.findById(id)
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
        if (!tagService.existsById(tagId)) {
            throw new ResourceNotFoundException("Tag no encontrado con el id = " + tagId);
        }

        List<TutorialEntity> tutorials = tutorialService.findTutorialsByTagsId(tagId);
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
                                         @RequestBody TagDto tagDto) {

        TagEntity tagEntity = tagMapper.mapFrom(tagDto);
        try {

            TagEntity tagSave = tutorialService.findById(tutorialId).map(tutorial -> {
                long tagId = tagEntity.getId();

                // tag is existed
                if (tagId != 0L) {
                    TagEntity tagById = tagService.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con el id = " + tagId));
                    tutorial.addTag(tagById);
                    tutorialService.save(tutorial);
                    return tagById;
                }

                // add and create new Tag
                tutorial.addTag(tagEntity);
                return tagService.save(tagEntity);
            }).orElseThrow(() -> new TutorialNotFoundException("Tutorial no encontrado = " + tutorialId));

            TagDto tagDtoSave = tagMapper.mapTo(tagSave);
            return CustomResponseHandler.generateResponse("Tags Creado",HttpStatus.OK, tagDtoSave) ;

        }catch(Exception ex){
            log.error(ex.getMessage());
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
    public ResponseEntity<Object> updateTag(@PathVariable("id") long id, @RequestBody TagEntity tagRequest) {
        TagEntity tag = tagService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id Tag " + id + "not found"));

        tag.setName(tagRequest.getName());

        return CustomResponseHandler.generateResponse("Tag editado",HttpStatus.OK, tagService.save(tag)) ;

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
        TutorialEntity tutorial = tutorialService.findById(tutorialId)
                .orElseThrow(() -> new ResourceNotFoundException("Id del Tutorial no encontrado, ID = " + tutorialId));

        tutorial.removeTag(tagId);
        tutorialService.save(tutorial);

        return CustomResponseHandler.generateResponse("Tag del tutorial eliminado ",HttpStatus.NO_CONTENT, tutorial) ;
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") long id) {
        tagService.deleteById(id);

        return CustomResponseHandler.generateResponse("Tag eliminado",HttpStatus.NO_CONTENT, id) ;

    }

}
