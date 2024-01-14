package com.jwt.seguridad.spring.springbootjwt.controller;

import com.jwt.seguridad.spring.springbootjwt.entity.Tutorial;
import com.jwt.seguridad.spring.springbootjwt.exception.TutorialNotFoundException;
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
public class TutorialController {

    private final  TutorialRepository tutorialRepository;

    @Autowired
    public TutorialController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    /**
     *
     * @param title
     * @return
     */
    @GetMapping("/tutorial")
    public ResponseEntity<Object> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        if (title == null)
            tutorialRepository.findAll().forEach(tutorials::add);
        else
            tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

        if (tutorials.isEmpty()) {
            return CustomResponseHandler.generateResponse("Listado de tutoriales vacio",HttpStatus.NO_CONTENT, tutorials) ;

        }

        return CustomResponseHandler.generateResponse("listado de tutoriales",HttpStatus.OK, tutorials) ;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/tutorial/{id}")
    public ResponseEntity<Object> getTutorialById(@PathVariable("id") long id) {
        Tutorial tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException("Tutorial no encontrado Id = " + id));

        return CustomResponseHandler.generateResponse("Tutorial encontrado",HttpStatus.OK, tutorial) ;
    }

    @PostMapping("/tutorial")
    public ResponseEntity<Object> createTutorial(@RequestBody Tutorial tutorial) {

        Tutorial tutorialNew = new Tutorial();
        tutorialNew.setTitle(tutorial.getTitle());
        tutorialNew.setDescription(tutorial.getDescription());
        tutorialNew.setPublished(tutorial.isPublished());

        return CustomResponseHandler.generateResponse("Tutorial encontrado"
                ,HttpStatus.OK, tutorialRepository.save(tutorialNew)) ;

    }

    /**
     *
     * @param id
     * @param tutorial
     * @return
     */
    @PutMapping("/tutorial/{id}")
    public ResponseEntity<Object> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Tutorial tutorialEditado = tutorialRepository.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException("Tutorial no encontrado = " + id));

        tutorialEditado.setTitle(tutorial.getTitle());
        tutorialEditado.setDescription(tutorial.getDescription());
        tutorialEditado.setPublished(tutorial.isPublished());

        return CustomResponseHandler.generateResponse("Tutorial modificado"
                ,HttpStatus.OK, tutorialRepository.save(tutorialEditado));

    }

    @DeleteMapping("/tutorial/{id}")
    public ResponseEntity<Object> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);

        return CustomResponseHandler.generateResponse("Tutorial eliminado"
                ,HttpStatus.NO_CONTENT, id) ;

    }

    @DeleteMapping("/tutorial")
    public ResponseEntity<Object> deleteAllTutorials() {
        tutorialRepository.deleteAll();

        return CustomResponseHandler.generateResponse("Todos los tutoriales elimados"
                ,HttpStatus.NO_CONTENT, "") ;
    }

    @GetMapping("/tutorial/published")
    public ResponseEntity<Object> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {

            return CustomResponseHandler.generateResponse("Listado vacio"
                    ,HttpStatus.NO_CONTENT, tutorials) ;
        }

        return CustomResponseHandler.generateResponse("Listado de tutoriales"
                ,HttpStatus.OK, tutorials) ;
    }

/*
    private final ITutorialService tutorialService;

    @GetMapping(path= "/tutorial/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllTutorials() {
        List<TutorialResponse> tutorialResponse = new ArrayList<TutorialResponse>();

        tutorialResponse = tutorialService.findAll();

        if (tutorialResponse.isEmpty()) {
            return CustomResponseHandler.generateResponse("Listado vacio",HttpStatus.NO_CONTENT, tutorialResponse) ;
        }
        return CustomResponseHandler.generateResponse("listado de tutoriales",HttpStatus.OK, tutorialResponse) ;

    }

    @GetMapping(path = "/tutorial/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTutorialById(@PathVariable("id") long id) {

        Optional<TutorialResponse> tutorialResponseOptional = tutorialService.findById(id);

        if(tutorialResponseOptional.isPresent()) {
            return CustomResponseHandler.
                    generateResponse("Tutorial encontrado", HttpStatus.OK,tutorialResponseOptional.get());
        }else {
            throw new TutorialNotFoundException("Tutorial no existe");
        }
    }

    @PostMapping(path = "/tutorial/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody @Valid @NotNull TutorialRequest tutorialRequest, BindingResult bidingResult) {

        if(bidingResult.hasErrors()){
            return CustomResponseHandler.
                    generateResponse(bidingResult
                                    .getAllErrors()
                                    .stream()
                                    .map(ObjectError::getDefaultMessage)
                                    .collect(Collectors.joining()),
                            HttpStatus.BAD_REQUEST,
                            tutorialRequest);
        }


        TutorialResponse tutorialResponse = tutorialService.saveTutorial(tutorialRequest);
        log.info("Tutorial {} guardado ", tutorialResponse.getId());
        return CustomResponseHandler.generateResponse("Tutorial Save",HttpStatus.CREATED, tutorialResponse) ;

    }
*/
    /*@PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.isPublished());

        return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }*/



}
