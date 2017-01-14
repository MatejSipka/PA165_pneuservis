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
        return "createNewAccount";
    }

    @RequestMapping(value = "/createNewAccount", method = RequestMethod.POST)
    public String createNewAccount(@Valid @ModelAttribute("personCreate") PersonDTO person,
                                   BindingResult bindingResult, Model model,
                                   RedirectAttributes redirectAttributes,
                                   UriComponentsBuilder uriBuilder){
        log.debug("person.create() GET Request", person);

        if (validatePersonDTO(person)) {
            redirectAttributes.addFlashAttribute("alert_failure1", "PLEASE FILL ALL FIELDS");
            return "redirect:" + uriBuilder.path("/createNewAccount").build().toUriString();
        }

        if (personFacade.findPersonByLogin(person.getLogin()) != null) {
            redirectAttributes.addFlashAttribute("alert_failure2", "Login already exists");
            return "redirect:" + uriBuilder.path("/createNewAccount").build().toUriString();
        }

        if (!person.getDateOfBirth().toString().matches("^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)" +
                "(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]" +
                "\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))" +
                "$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")){
            redirectAttributes.addFlashAttribute("alert_failure3", "Wrong date format");
            return "redirect:" + uriBuilder.path("/createNewAccount").build().toUriString();
        }

        Long id = personFacade.create(person, person.getPasswordHash());
        redirectAttributes.addFlashAttribute("alert_success", "Person " + id + " was created");
        return "/login";
    }

    private boolean validatePersonDTO(PersonDTO personDTO) {
        if (personDTO.getDateOfBirth() == null || personDTO.getFirstname() == null || personDTO.getPasswordHash() == null
                || personDTO.getSurname() == null || personDTO.getLogin() == null){
            return true;
        }
        return false;

    }

}

/*
if (bindingResult.hasFieldErrors()){

            for(FieldError error: bindingResult.getFieldErrors()){

                if (error.getField().equals("firstname_error")) {
                    redirectAttributes.addFlashAttribute("alert_failure", "firstName is empty!");
                }
                if (error.getField().equals("lastname_error")) {
                    redirectAttributes.addFlashAttribute("alert_failure", "surname is empty!");
                }
                if (error.getField().equals("dateOfBirth_error")) {
                    redirectAttributes.addFlashAttribute("alert_failure", "dateOfBirth is empty");
                }

                if (error.getField().equals("login") && person.getDateOfBirth() != null){
                    try {
                        String date = (String) error.getRejectedValue();
                        if (!date.matches("^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[1,3-9]|1[0-2])\\" +
                                "2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|" +
                                "[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8]" +
                                ")(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")){
                            redirectAttributes.addFlashAttribute("alert_failure", "dateOfBirth has wrong format");
                        }
                    } catch (ClassCastException e){
                        throw new ClassCastException("could not cast error to string");
                    }
                }

                if (error.getField().equals("login_error") || person.getLogin() == null) {
                    redirectAttributes.addFlashAttribute("alert_failure", "login is empty!");
                }
                if (error.getField().equals("login") && person.getLogin() != null){
                    try {
                        String login = (String) error.getRejectedValue();
                        if (personFacade.findPersonByLogin(login) != null){
                            redirectAttributes.addFlashAttribute("alert_failure", "login already exits!");
                        }
                    } catch (ClassCastException e){
                        throw new ClassCastException("could not cast error to string");
                    }
                }

                if (error.getField().equals("name")) {
                    redirectAttributes.addFlashAttribute("alert_failure", "password is empty!");
                }
            }
        }
 */
