package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;

/**
 * @author Ivan Moscovic on 16.12.2016.
 */
@Controller
@RequestMapping("/")
public class NewAccountController {

    final static Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonFacade personFacade;

    @RequestMapping(value = "/createNewAccount", method = RequestMethod.GET)
    public String createNewAccount(Model model){
        log.debug("person.create() GET Request");
        model.addAttribute("personCreate", new PersonDTO());
        model.addAttribute("NewUser", "true");
        System.out.println(model.containsAttribute("NewUser"));
        return "createNewAccount";
    }

    @RequestMapping(value = "/createNewAccount", method = RequestMethod.POST)
    public String createNewAccount(@Valid @ModelAttribute("personCreate") PersonDTO personForm,
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
            return "createNewAccount";
        }
        Long id = personFacade.create(personForm, personForm.getPasswordHash());
        redirectAttributes.addFlashAttribute("alert_success", "Person " + id + " was created");
        return "/login";
    }
}
