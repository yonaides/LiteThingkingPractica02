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

    /**
     *
     * @param tutorial
     * @return
     */
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

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/tutorial/{id}")
    public ResponseEntity<Object> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);

        return CustomResponseHandler.generateResponse("Tutorial eliminado"
                ,HttpStatus.NO_CONTENT, id) ;

    }

    /**
     *
     * @return
     */
    @DeleteMapping("/tutorial")
    public ResponseEntity<Object> deleteAllTutorials() {
        tutorialRepository.deleteAll();

        return CustomResponseHandler.generateResponse("Todos los tutoriales elimados"
                ,HttpStatus.NO_CONTENT, "") ;
    }

    /**
     *
     * @return
     */
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


}
