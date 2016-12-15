/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Maros Staurovsky
 */

@Controller
@RequestMapping("/login")
public class loginController {

    @Autowired 
    private HttpSession session;

    @Autowired
    private PersonFacade personFacade;
    
    @RequestMapping(value="/check", method=RequestMethod.POST)
    public String Check(Model model, @RequestParam("login") String login, @RequestParam("password") String password) {
        PersonDTO person = personFacade.findPersonByLogin(login);
        System.out.println("mam login: "+ login);
             
        if (person == null) {
            return "redirect:/";
        }
        PersonDTO authDTO = new PersonDTO();
        authDTO.setId(person.getId());
        authDTO.setPasswordHash(password);
        System.out.println("mam password: "+ password);
        if (personFacade.authenticate(authDTO)) {
        System.out.println("mam login: "+ login + "A HESLO: " + password);
            session.setAttribute("authenticated", person);
            return "redirect:/home"; 
            
        }
            return "redirect:/";
        }
    }
