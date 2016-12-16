/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.rest;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.AlreadyExistingException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.BadRequestException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.NotFoundException;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Maros Staurovsky
 */
@RestController
@RequestMapping("pa165/rest/persons")
public class PersonRestController {
    
    final static Logger log = LoggerFactory.getLogger(PersonRestController.class);

    @Inject
    private PersonFacade personFacade;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<PersonDTO> get() {
        log.debug("rest person.findAll()");
        return personFacade.findAll();
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<PersonDTO> post(@RequestBody PersonDTO personDTO) throws Exception {
        log.debug("rest person.create()");
        try {
            Long id = personFacade.create(personDTO, "TODO!!!!" );
            return new ResponseEntity<>(personFacade.findById(id), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new AlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO get(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest person.findById({})", id);
        PersonDTO personDTO = personFacade.findById(id);

        if (personDTO != null) {
            return personDTO;
        } else {
            throw new BadRequestException();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<PersonDTO> put(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id,
                                 @RequestBody PersonDTO newPerson) throws Exception {
        log.debug("rest person.update({})", id);
        try {
            newPerson.setId(id);
            personFacade.update(newPerson);
            return new ResponseEntity<>(personFacade.findById(id), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity delete(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest person.delete({})", id);
        PersonDTO personDTO = personFacade.findById(id);
        if (personDTO == null) throw new BadRequestException();
        personFacade.delete(personDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
}
