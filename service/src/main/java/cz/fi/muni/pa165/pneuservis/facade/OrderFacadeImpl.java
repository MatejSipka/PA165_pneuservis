package cz.fi.muni.pa165.pneuservis.facade;

import cz.fi.muni.pa165.pneuservis.dto.*;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.facade.OrderFacade;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.OrderBilling;
import cz.fi.muni.pa165.pneuservis.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vit.holasek on 23.11.2016.
 */
@Service
@Transactional
public class OrderFacadeImpl implements OrderFacade {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public OrderDTO create(CreateOrderDTO order) {
        Order orderEntity = beanMappingService.mapTo(order, Order.class);
        orderService.create(orderEntity);
        return beanMappingService.mapTo(orderEntity, OrderDTO.class);
    }

    @Override
    public void update(UpdateOrderDTO order) {
        Order orderEntity = beanMappingService.mapTo(order, Order.class);
        orderService.update(orderEntity);
    }

    @Override
    public void delete(OrderDTO order) {
        Order orderEntity = beanMappingService.mapTo(order, Order.class);
        orderService.delete(orderEntity);
    }

    @Override
    public OrderDTO findOrderById(Long orderId) {
        Order order = orderService.findOrderById(orderId);
        return beanMappingService. mapTo(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findClientOrders(Long clientId) {
        List<Order> orders = orderService.findClientOrders(clientId);
        return beanMappingService.mapTo(orders, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        List<Order> orders = orderService.findAllOrders();
        return beanMappingService.mapTo(orders, OrderDTO.class);
    }

    @Override
    public OrderBillingDTO getOrderBilling(Long orderId) {
        OrderBilling billing = orderService.getOrderBilling(orderId);
        OrderBillingDTO orderBillingDTO = beanMappingService.mapTo(billing, OrderBillingDTO.class);
        orderBillingDTO.setOrderId(billing.getOrder().getId());
        return orderBillingDTO;
    }
}
