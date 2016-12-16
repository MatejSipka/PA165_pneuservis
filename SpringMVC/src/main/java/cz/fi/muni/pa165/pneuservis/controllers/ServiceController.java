/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import static cz.fi.muni.pa165.pneuservis.enums.PersonType.EMPLOYEE;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cz.fi.muni.pa165.pneuservis.facade.ServiceFacade;
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
 * @author Ivan Moscovic
 */
@Controller
@RequestMapping("/service")
public class ServiceController {

    final static Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private ServiceFacade serviceFacade;

    @Autowired
    private PersonFacade personFacade;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("services.findAll()");
        model.addAttribute("services", serviceFacade.findAllServices());
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "service/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        log.debug("services.create() GET Request");
        model.addAttribute("serviceCreate", new ServiceDTO());

        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "service/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("serviceCreate") ServiceDTO serviceDTO,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        log.debug("service.create() GET Request", serviceDTO);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "service/create";
        }
        Long id = serviceFacade.create(serviceDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Service " + id + " was created");
        return "redirect:" + uriBuilder.path("/service/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        log.debug("person.findById()");
        ServiceDTO serviceView = serviceFacade.findById(id);
        model.addAttribute("service", serviceView);
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "service/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        log.debug("services");
        ServiceDTO serviceDTO = serviceFacade.findById(id);

        try {
            serviceFacade.delete(serviceDTO);
        } catch (Exception e) {
            System.out.println(model.containsAttribute("fail"));
            model.addAttribute("fail", "yes");
            return "service/list";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Service successfully deleted.");
        return "redirect:" + uriBuilder.path("/service/list").toUriString();
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable("id") Long id) {
        model.addAttribute("service", serviceFacade.findById(id));
        return "service/view";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {

        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        model.addAttribute("serviceId",id);
        model.addAttribute("serviceCreate", serviceFacade.findById(id));
        return "service/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("serviceCreate") ServiceDTO serviceDTO,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "service/edit";
        }
        long id = serviceFacade.update(serviceDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Service " + id + " has been updated.");
        return "redirect:" + uriBuilder.path("/service/list").toUriString();
    }
}

