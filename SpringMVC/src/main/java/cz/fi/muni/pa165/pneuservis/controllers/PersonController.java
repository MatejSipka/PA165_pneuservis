/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import static cz.fi.muni.pa165.pneuservis.enums.PersonType.EMPLOYEE;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Maros Staurovsky
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    private PersonDTO tmp;
    final static Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonFacade personFacade;
    
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("person.findAll()");
        model.addAttribute("persons", personFacade.findAll());
        System.out.println("What: " + personFacade.findById(1L).getDateOfBirth());
        System.out.println("What: " + personFacade.findById(1L).getPersonType());
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "person/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        log.debug("person.create() GET Request");
        model.addAttribute("personCreate", new PersonDTO());
        
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "person/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("personCreate") PersonDTO personForm,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder){
        log.debug("person.create() GET Request", personForm);
        
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "person/create";
        }
        Long id = personFacade.create(personForm, personForm.getPasswordHash());
        redirectAttributes.addFlashAttribute("alert_success", "Person " + id + " was created");
        return "redirect:" + uriBuilder.path("/person/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("person.findById()");
        PersonDTO personView = personFacade.findById(id);
        model.addAttribute("person", personView);
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "person/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        log.debug("person.");
        PersonDTO person = personFacade.findById(id);
        personFacade.delete(person);

        redirectAttributes.addFlashAttribute("alert_success", "Person successfully deleted.");
        return "redirect:" + uriBuilder.path("/person/list").toUriString();
    }
    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("id") Long id) {
        model.addAttribute("person", personFacade.findById(id));
        return "person/view";
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String change(Model model, @PathVariable("id") Long id) {
        model.addAttribute("person", new PersonDTO());
        model.addAttribute("data", personFacade.findById(id));
        model.addAttribute("firstname", personFacade.findById(id).getFirstname());
        model.addAttribute("surname", personFacade.findById(id).getSurname());
        model.addAttribute("personType", personFacade.findById(id).getPersonType());
        model.addAttribute("login",personFacade.findById(id).getLogin());
        model.addAttribute("dateOfBirth",personFacade.findById(id).getDateOfBirth());
        
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        tmp = person;
        return "person/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("mushroom") PersonDTO formBean, @PathVariable("id") Long id, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        formBean.setId(id);
//        if (formBean.getDateOfBirth().getTime() == NULL)
//        {
//            
//        }
        return view(model, personFacade.update(formBean).getId());
    }

}
