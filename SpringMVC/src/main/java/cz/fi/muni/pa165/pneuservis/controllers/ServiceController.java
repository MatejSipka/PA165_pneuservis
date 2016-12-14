package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;
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
import javax.validation.Valid;

/**
 * @author Ivan Moscovic on 13.12.2016.
 */
@Controller
@RequestMapping("/services")
public class ServiceController {

    final static Logger log = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private ServiceFacade serviceFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        log.debug("find all");
        model.addAttribute("services", serviceFacade.findAllServices());
        return "services/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") long id, Model model) {
        log.debug("view({})", id);
        model.addAttribute("service", serviceFacade.findById(id));
        return "services/view";
    }

    @RequestMapping(value = "/view/{name}", method = RequestMethod.GET)
    public String view(@PathVariable("name") String name, Model model) {
        log.debug("view({})", name);
        model.addAttribute("services", serviceFacade.findByName(name));
        return "services/view";
    }

    @RequestMapping(value = "/delete{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, Model model, UriComponentsBuilder uri, RedirectAttributes attributes){
        ServiceDTO service = serviceFacade.findById(id);
        Long idService = serviceFacade.delete(service);
        log.debug("delete({})", idService);
        attributes.addFlashAttribute("alert_success", "Service with id " + String.valueOf(id) + " was deleted");
        return "redirect:" + uri.path("services/list").toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newService(Model model) {
        log.debug("new()");
        model.addAttribute("serviceCreate", new ServiceDTO());
        return "services/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("serviceCreate") ServiceDTO serviceDTO, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("service Create", serviceDTO);

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "services/new";
        }
        Long id = serviceFacade.create(serviceDTO);
        redirectAttributes.addFlashAttribute("alert_success", "service " + String.valueOf(id) + " was created");
        return "redirect:" + uriBuilder.path("/services/list").toUriString();
    }

}
