package com.jwt.seguridad.spring.springbootjwt.controller;

import com.jwt.seguridad.spring.springbootjwt.dto.TutorialDto;
import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import com.jwt.seguridad.spring.springbootjwt.exception.TutorialNotFoundException;
import com.jwt.seguridad.spring.springbootjwt.helper.CheckRequestErrorHandler;
import com.jwt.seguridad.spring.springbootjwt.mapper.mappers.Mapper;
import com.jwt.seguridad.spring.springbootjwt.response.CustomResponseHandler;
import com.jwt.seguridad.spring.springbootjwt.services.ITutorialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class TutorialController {

    private final ITutorialService tutorialService;
    private final Mapper<TutorialEntity, TutorialDto> tutorialMapper;

    @Autowired
    public TutorialController(ITutorialService tutorialService,
                              Mapper<TutorialEntity, TutorialDto> tutorialMapper) {
        this.tutorialService = tutorialService;
        this.tutorialMapper = tutorialMapper;
    }

    /**
     *
     * @param title
     * @return
     */
    @GetMapping("/tutorial")
    public ResponseEntity<Object> getAllTutorials(@RequestParam(required = false) String title) {
        List<TutorialDto> tutorialsDto;
        List<TutorialEntity> listadoTutoriales = new ArrayList<>();

        if (title == null) {
            tutorialService.findAll().forEach(listadoTutoriales::add);
        }else{
            tutorialService.findByTitleContaining(title).forEach(listadoTutoriales::add);
        }

        tutorialsDto = listadoTutoriales.stream().map(this::mapToTutorialDto).toList();

        if (tutorialsDto.isEmpty()) {
            return CustomResponseHandler.generateResponse("Listado de tutoriales vacio",HttpStatus.NO_CONTENT, tutorialsDto) ;
        }

        return CustomResponseHandler.generateResponse("listado de tutoriales",HttpStatus.OK, tutorialsDto) ;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/tutorial/{id}")
    public ResponseEntity<Object> getTutorialById(@PathVariable("id") long id) {
        TutorialEntity tutorial = tutorialService.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException("Tutorial no encontrado Id = " + id));

        TutorialDto tutorialDto = tutorialMapper.mapTo(tutorial);
        return CustomResponseHandler.generateResponse("Tutorial encontrado",HttpStatus.OK, tutorialDto) ;
    }

    /**
     *
     * @param tutorialDto
     * @param bidingResult
     * @return
     */
    @PostMapping("/tutorial")
    public ResponseEntity<Object> createTutorial(@RequestBody @Valid @NotNull TutorialDto tutorialDto, BindingResult bidingResult) {

        if(bidingResult.hasErrors()){
            return CheckRequestErrorHandler.responseErrorsMessage(bidingResult, tutorialDto);
        }

        TutorialEntity tutorialEntity = tutorialMapper.mapFrom(tutorialDto);
        TutorialEntity tutorialEntitySave =  tutorialService.save(tutorialEntity);
        TutorialDto tutorialDtoSve = tutorialMapper.mapTo(tutorialEntitySave);

        return CustomResponseHandler.generateResponse("Tutorial creado"
                ,HttpStatus.OK, tutorialDtoSve) ;

    }

    /**
     *
     * @param id
     * @param tutorialDto
     * @param bidingResult
     * @return
     */
    @PutMapping("/tutorial/{id}")
    public ResponseEntity<Object> updateTutorial(@PathVariable("id") long id,
                                                 @Valid
                                                 @NotNull
                                                 @RequestBody
                                                 TutorialDto tutorialDto,
                                                 BindingResult bidingResult) {

        if(bidingResult.hasErrors()){
            return CheckRequestErrorHandler.responseErrorsMessage(bidingResult, tutorialDto);
        }

        TutorialEntity tutorialEntity = tutorialService.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException("Tutorial no encontrado = " + id));

        tutorialEntity.setTitle(tutorialDto.getTitle());
        tutorialEntity.setDescription(tutorialDto.getDescription());
        tutorialEntity.setPublished(tutorialDto.isPublished());

        TutorialEntity tutorialSave = tutorialService.save(tutorialEntity);
        TutorialDto tutorialDtoSave = tutorialMapper.mapTo(tutorialSave);

        return CustomResponseHandler.generateResponse("Tutorial modificado"
                ,HttpStatus.OK, tutorialDtoSave);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/tutorial/{id}")
    public ResponseEntity<Object> deleteTutorial(@PathVariable("id") long id) {
        tutorialService.deleteById(id);

        return CustomResponseHandler.generateResponse("Tutorial eliminado"
                ,HttpStatus.NO_CONTENT, id) ;

    }

    /**
     *
     * @return
     */
    @DeleteMapping("/tutorial")
    public ResponseEntity<Object> deleteAllTutorials() {
        tutorialService.deleteAll();

        return CustomResponseHandler.generateResponse("Todos los tutoriales elimados"
                ,HttpStatus.NO_CONTENT, "") ;
    }

    /**
     *
     * @return
     */
    @GetMapping("/tutorial/published")
    public ResponseEntity<Object> findByPublished() {
        List<TutorialEntity> tutorials = tutorialService.findByPublished(true);

        if (tutorials.isEmpty()) {

            return CustomResponseHandler.generateResponse("Listado vacio"
                    ,HttpStatus.NO_CONTENT, tutorials) ;
        }

        return CustomResponseHandler.generateResponse("Listado de tutoriales publicados"
                ,HttpStatus.OK, tutorials) ;
    }

    private TutorialDto mapToTutorialDto(TutorialEntity tutorial) {
        return tutorialMapper.mapTo(tutorial);
    }

}
