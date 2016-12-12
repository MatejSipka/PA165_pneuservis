package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author vit.holasek on 23.11.2016.
 */
public interface OrderService {
    /**
     * Create order.
     * @param order Order to create
     * @return Order with generated ID
     */
    Order create(Order order);

    /**
     * Update order information.
     * @param order Order to update
     */
    void update(Order order);

    /**
     * Delete order.
     * @param order Order ID
     */
    void delete(Order order);


    /**
     * Find order by given ID.
     * @param orderId Order ID
     * @return Order
     */
    Order findOrderById(Long orderId);

    /**
     * Find all orders related to the client with given ID.
     * @param clientId Client ID
     * @return list of orders
     */
    List<Order> findClientOrders(Long clientId);

    /**
     * Find all orders.
     * @return List of orders
     */
    List<Order> findAllOrders();

    /**
     * Generate billing information of order with given ID.
     * @param orderId Order ID
     * @return Billing information
     */
    OrderBilling getOrderBilling(Long orderId);

    /**
     * Calculate total profit from already payed orders
     * @return profit sum
     */
    BigDecimal calculateProfit();
}
