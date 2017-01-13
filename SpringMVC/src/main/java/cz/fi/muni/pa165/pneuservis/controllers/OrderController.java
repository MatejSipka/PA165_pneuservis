package cz.fi.muni.pa165.pneuservis.controllers;

import cz.fi.muni.pa165.pneuservis.dto.*;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.facade.OrderFacade;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.facade.ServiceFacade;
import cz.fi.muni.pa165.pneuservis.facade.TireFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.BadRequestException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.ForbiddenException;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static cz.fi.muni.pa165.pneuservis.enums.PersonType.EMPLOYEE;

/**
 * Created by vit.holasek on 15.12.2016.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private PersonFacade personFacade;

    @Autowired
    private ServiceFacade serviceFacade;

    @Autowired
    private TireFacade tireFacade;

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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        CreateOrderDTO order = new CreateOrderDTO();
        order.setServices(new HashSet<>());
        order.setTires(new HashSet<>());
        model.addAttribute("order", order);
        model.addAttribute("paymentTypeValues", PaymentType.values());
        session.setAttribute("tmpOrder", order);
        return "order/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRedirectBack(Model model){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        model.addAttribute("order", tmpOrder);
        model.addAttribute("paymentTypeValues", PaymentType.values());
        return "order/create";
    }

    @RequestMapping(value = "/create/submit", method = RequestMethod.POST)
    public String createSubmit(@Valid @ModelAttribute("order") CreateOrderDTO order,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               UriComponentsBuilder uriBuilder){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            model.addAttribute("paymentTypeValues", PaymentType.values());
            model.addAttribute("order", tmpOrder);
            return "order/create";
        }
        if (tmpOrder.getServices().size() == 0 && tmpOrder.getTires().size() == 0)
        {
            model.addAttribute("items_error", true);
            return "order/create";
        }
        tmpOrder.setPaymentType(order.getPaymentType());
        tmpOrder.setNote(order.getNote());
        if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE && order.getClientId() != null) {
            tmpOrder.setClientId(order.getClientId());
        } else {
            tmpOrder.setClientId(person.getId());
        }
        OrderDTO orderDTO = orderFacade.create(tmpOrder);
        session.setAttribute("tmpOrder", null);
        redirectAttributes.addFlashAttribute("alert_success", "Order " + orderDTO.getId() + " was created");
        return "redirect:" + uriBuilder.path("/order/{id}/view").buildAndExpand(orderDTO.getId()).encode().toUriString();
    }

    @RequestMapping(value = "/create/services", method = RequestMethod.POST)
    public String servicesView(@Valid @ModelAttribute("order") CreateOrderDTO order,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               UriComponentsBuilder uriBuilder){
        List<ServiceDTO> services = serviceFacade.findAllServices();
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        tmpOrder.setPaymentType(order.getPaymentType());
        tmpOrder.setNote(order.getNote());
        model.addAttribute("services", services);
        model.addAttribute("order", tmpOrder);
        return "order/services";
    }

    @RequestMapping(value = "/create/services/{id}", method = RequestMethod.POST)
    public String createAddService(@PathVariable long id, Model model){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        ServiceDTO service = serviceFacade.findById(id);
        if (tmpOrder.getServices() == null) tmpOrder.setServices(new HashSet<>());
        tmpOrder.getServices().add(service);
        model.addAttribute("order", tmpOrder);
        model.addAttribute("paymentTypeValues", PaymentType.values());
        return "order/create";
    }

    @RequestMapping(value = "/create/services/{id}/remove", method = RequestMethod.POST)
    public String createRemoveService(@PathVariable long id, @Valid @ModelAttribute("order") CreateOrderDTO order,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   UriComponentsBuilder uriBuilder){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        tmpOrder.setPaymentType(order.getPaymentType());
        tmpOrder.setNote(order.getNote());
        if (tmpOrder.getServices() != null) {
            ServiceDTO s = null;
            for (ServiceDTO service : tmpOrder.getServices()
                    ) {
                if (service.getId() == id) {
                    s = service;
                    break;
                }
            }
            if (s != null) tmpOrder.getServices().remove(s);
        }
        model.addAttribute("order", tmpOrder);
        model.addAttribute("paymentTypeValues", PaymentType.values());
        return "order/create";
    }

    @RequestMapping(value = "/create/tires", method = RequestMethod.POST)
    public String tiresView(@Valid @ModelAttribute("order") CreateOrderDTO order,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               UriComponentsBuilder uriBuilder){
        List<TireDTO> tires = tireFacade.findAll();
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        tmpOrder.setPaymentType(order.getPaymentType());
        tmpOrder.setNote(order.getNote());
        model.addAttribute("tires", tires);
        model.addAttribute("order", tmpOrder);
        return "order/tires";
    }

    @RequestMapping(value = "/create/tires/{id}", method = RequestMethod.POST)
    public String createAddTire(@PathVariable long id, Model model){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        TireDTO tire = tireFacade.findById(id);
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        if (tmpOrder.getTires() == null) tmpOrder.setTires(new HashSet<>());
        tmpOrder.getTires().add(tire);
        model.addAttribute("order", tmpOrder);
        model.addAttribute("paymentTypeValues", PaymentType.values());
        return "order/create";
    }

    @RequestMapping(value = "/create/tires/{id}/remove", method = RequestMethod.POST)
    public String createTireService(@PathVariable long id, @Valid @ModelAttribute("order") CreateOrderDTO order,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes,
                                      UriComponentsBuilder uriBuilder){
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        CreateOrderDTO tmpOrder = CreateOrderDTO.class.cast(session.getAttribute("tmpOrder"));
        tmpOrder.setPaymentType(order.getPaymentType());
        tmpOrder.setNote(order.getNote());
        if (tmpOrder.getTires() != null) {
            TireDTO t = null;
            for (TireDTO tire : tmpOrder.getTires()
                    ) {
                if (tire.getId() == id) {
                    t = tire;
                    break;
                }
            }
            if (t != null) tmpOrder.getTires().remove(t);
        }
        model.addAttribute("order", tmpOrder);
        model.addAttribute("paymentTypeValues", PaymentType.values());
        return "order/create";
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable long id,
                         Model model,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        OrderDTO order = orderFacade.findOrderById(id);
        if (order != null) orderFacade.delete(order);

        redirectAttributes.addFlashAttribute("alert_success", "Order successfully deleted.");
        return "redirect:" + uriBuilder.path("/order/list").toUriString();
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {

        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() == EMPLOYEE) {
                model.addAttribute("Admin", person.getLogin());
            } else {
                model.addAttribute("User", person.getLogin());
            }
        }
        OrderDTO order = orderFacade.findOrderById(id);
        model.addAttribute("order", orderFacade.findOrderById(id));
        model.addAttribute("paymentTypeValues", PaymentType.values());
        return "order/edit";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("serviceCreate") UpdateOrderDTO order,
                       BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "order/edit";
        }
        OrderDTO orderToUpdate = orderFacade.findOrderById(order.getId());
        if (orderToUpdate == null) throw new BadRequestException();
        order.setServices(orderToUpdate.getServices());
        order.setTires(orderToUpdate.getTires());
        orderFacade.update(order);
        redirectAttributes.addFlashAttribute("alert_success", "Service " + order.getId() + " has been updated.");
        return "redirect:" + uriBuilder.path("/order/list").toUriString();
    }

    @RequestMapping(value = "{id}/billing", method = RequestMethod.GET)
    public String billing(@PathVariable long id, Model model) {
        OrderDTO order = orderFacade.findOrderById(id);
        PersonDTO person = PersonDTO.class.cast(session.getAttribute("authenticated"));
        if (person != null) {
            if (personFacade.findById(person.getId()).getPersonType() != EMPLOYEE && order.getClientId() != person.getId()) {
                throw new ForbiddenException();
            }
        }
        OrderBillingDTO billing = orderFacade.getOrderBilling(id);
        model.addAttribute("billing", billing);
        model.addAttribute("person", person);
        model.addAttribute("order", order);
        return "order/billing";
    }
}
