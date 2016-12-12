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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Maros Staurovsky
 */
@Controller
@RequestMapping("/home")
public class homeController {
    @Autowired
    PersonFacade personFacade;
    
    @Autowired
    HttpSession session;
    
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        return "/home";
    }
    
}
