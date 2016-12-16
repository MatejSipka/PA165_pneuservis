package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.OrderDTO;
import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.facade.OrderFacade;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static cz.fi.muni.pa165.pneuservis.enums.PersonType.EMPLOYEE;

/**
 * Created by vit.holasek on 15.12.2016.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    final static Logger log = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private PersonFacade personFacade;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<OrderDTO> orders = new ArrayList<>();
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
                orders = orderFacade.findAllOrders();
            } else {
                model.addAttribute("User", person.getLogin());
                orders = orderFacade.findClientOrders(person.getId());
            }
        }
        model.addAttribute("orders", orders);
        return "order/list";
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        OrderDTO order = orderFacade.findOrderById(id);
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                if (order.getClientId() != person.getId()) throw new ForbiddenException();
                model.addAttribute("User", person.getLogin());
            }
        }
        model.addAttribute("person", person);
        model.addAttribute("order", order);
        return "order/view";
    }
}
