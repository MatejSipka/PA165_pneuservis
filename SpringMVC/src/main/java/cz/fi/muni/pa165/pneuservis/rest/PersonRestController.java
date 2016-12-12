/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.rest;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.AlreadyExistingException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.NotFoundException;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Maros Staurovsky
 */
@RestController
@RequestMapping("/rest/person")
public class PersonRestController {
    
    final static Logger log = LoggerFactory.getLogger(PersonRestController.class);

    @Inject
    private PersonFacade personFacade;

    @RequestMapping(value = "/list",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<PersonDTO> list() {
        log.debug("rest person.findAll()");
        return personFacade.findAll();
    }

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO create(@RequestBody PersonDTO personDTO) throws Exception {
        log.debug("rest person.create()");
        try {
            Long id = personFacade.create(personDTO, "TODO!!!!" );
            return personFacade.findById(id);
        } catch (Exception ex) {
            throw new AlreadyExistingException();
        }
    }

    @RequestMapping(value = "/view/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO view(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest person.findById({})", id);
        PersonDTO personDTO = personFacade.findById(id);

        if (personDTO != null) {
            return personDTO;
        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final PersonDTO update(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id,
                                 @RequestBody PersonDTO newPerson) throws Exception {
        log.debug("rest person.update({})", id);

        try {
            newPerson.setId(id);
            personFacade.update(newPerson);
            return personFacade.findById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest person.delete({})", id);
        PersonDTO personDTO = personFacade.findById(id);

        try {
            personFacade.delete(personDTO);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }
    
}
