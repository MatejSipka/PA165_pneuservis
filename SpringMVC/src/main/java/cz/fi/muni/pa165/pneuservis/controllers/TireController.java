/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.dto.TireDTO;
import static cz.fi.muni.pa165.pneuservis.enums.PersonType.EMPLOYEE;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.facade.TireFacade;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
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
 * @author Matej Sipka
 */
@Controller
@RequestMapping("/tires")
public class TireController {

    private TireDTO tmp;
    final static Logger log = LoggerFactory.getLogger(TireController.class);

    @Autowired
    private TireFacade tireFacade;

    @Autowired
    private PersonFacade personFacade;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        model = checkForPermissions(model);
        model.addAttribute("tires", tireFacade.findAll());

        return "tires/list";
    }

	
    
    private boolean validateTireDTO(TireDTO tireDTO) {

        return tireDTO.getPrice() == null || tireDTO.getCatalogNumber() == 0 || tireDTO.getDiameter() == 0 || tireDTO.getManufacturer() == null
                || tireDTO.getPrice() == null || tireDTO.getTireSize() == 0 || tireDTO.getType() == null;

    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {

        model = checkForPermissions(model);
        model.addAttribute("tireCreate", new TireDTO());
        model.addAttribute("manufacturers", TireManufacturer.values());
        model.addAttribute("seasons", TireType.values());

        return "tires/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("tireCreate") TireDTO tireDTO,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        if (validateTireDTO(tireDTO)) {
		redirectAttributes.addFlashAttribute("alert_success", "PLEASE FILL ALL FIELDS. VALUES HAVE TO BE A POSITIVE NUMBER.");
		return "redirect:" + uriBuilder.path("/tires/create").build().toUriString();
	}


        tireFacade.create(tireDTO);
        int id = tireDTO.getCatalogNumber();
        redirectAttributes.addFlashAttribute("alert_success", "Tire with a catalog number " + id + " was created");
        return "redirect:" + uriBuilder.path("/tires/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
            Model model,
            UriComponentsBuilder uriBuilder,
            RedirectAttributes redirectAttributes) {

        TireDTO tire = tireFacade.findById(id);
        try {
            tireFacade.delete(tire);
        } catch (Exception e) {
            return "redirect:/tires/list?deleteError";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Tire successfully deleted.");
        return "redirect:" + uriBuilder.path("/tires/list").toUriString();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {

        model = checkForPermissions(model);
        model.addAttribute("tireId",id);
        model.addAttribute("tireCreate", tireFacade.findById(id));
        model.addAttribute("manufacturers", TireManufacturer.values());
        model.addAttribute("seasons", TireType.values());
        return "tires/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("tireCreate") TireDTO tireDTO,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

   //     if (validateTireDTO(tireDTO)) {
     //           redirectAttributes.addFlashAttribute("alert_success", "PLEASE FILL ALL FIELDS.");
//		return "redirect:" + uriBuilder.path("/tires/edit/"+ pathId).build().toUriString();
  //      }

        tireFacade.update(tireDTO);
        int id = tireDTO.getCatalogNumber();
        redirectAttributes.addFlashAttribute("alert_success", "Tire with a catalog number " + id + " has been updated.");
        return "redirect:" + uriBuilder.path("/tires/list").toUriString();
    }

    private Model checkForPermissions(Model model) {
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }

        return model;
    }

}
