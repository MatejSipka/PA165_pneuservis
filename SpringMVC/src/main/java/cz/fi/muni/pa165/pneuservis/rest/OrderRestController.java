package cz.fi.muni.pa165.pneuservis.rest;

import cz.fi.muni.pa165.pneuservis.dto.CreateOrderDTO;
import cz.fi.muni.pa165.pneuservis.dto.OrderBillingDTO;
import cz.fi.muni.pa165.pneuservis.dto.OrderDTO;
import cz.fi.muni.pa165.pneuservis.dto.UpdateOrderDTO;
import cz.fi.muni.pa165.pneuservis.facade.OrderFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vit.holasek on 16.12.2016.
 */
@RestController
@RequestMapping("/pa165/rest/orders")
public class OrderRestController {
    @Inject
    private OrderFacade orderFacade;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<OrderDTO> get() {
        return orderFacade.findAllOrders();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final OrderDTO get(@PathVariable long id) {
        OrderDTO order = orderFacade.findOrderById(id);
        if (order == null) throw new BadRequestException();
        return order;
    }

    @RequestMapping(value = "/{id}/billing",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final OrderBillingDTO getBilling(@PathVariable long id) {
        OrderDTO order = orderFacade.findOrderById(id);
        if (order == null) throw new BadRequestException();
        return orderFacade.getOrderBilling(id);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<OrderDTO> post(@RequestBody CreateOrderDTO order) {
        return new ResponseEntity<>(orderFacade.create(order), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity put(@PathVariable long id, @RequestBody UpdateOrderDTO order) {
        OrderDTO gatheredOrder = orderFacade.findOrderById(id);
        if (gatheredOrder == null) throw new BadRequestException();
        orderFacade.update(order);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public final ResponseEntity delete(@PathVariable long id) {
        OrderDTO order = orderFacade.findOrderById(id);
        if (order == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        orderFacade.delete(order);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
