package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.dao.OrderDAO;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vit.holasek on 24.11.2016.
 */
@org.springframework.stereotype.Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDAO orderDao;

    private int vat = 21;

    @Override
    public Order create(Order order) {
        if (order == null) throw new IllegalArgumentException("Order is null.");
        order.setShipped(false);
        order.setPaymentConfirmed(false);
        try {
            orderDao.create(order);
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot create order", e);
        }
        return order;
    }

    @Override
    public void update(Order order) {
        if (order == null) throw new IllegalArgumentException("Order is null.");
        try {
            orderDao.update(order);
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot update order", e);
        }
    }

    @Override
    public void delete(Order order) {
        if (order == null) throw new IllegalArgumentException("Order is null.");
        try {
            orderDao.delete(order);
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot delete order", e);
        }
    }

    @Override
    public Order findOrderById(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("OrderId is null.");
        try {
            return orderDao.findById(orderId);
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot find order with id: " + orderId, e);
        }
    }

    @Override
    public List<Order> findClientOrders(Long clientId) {
        if (clientId == null) throw new IllegalArgumentException("ClientId is null.");
        try {
            return orderDao.findByClientId(clientId);
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot find orders with clientId: " + clientId, e);
        }
    }

    @Override
    public List<Order> findAllOrders() {
        try {
            return orderDao.findAll();
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot find orders.", e);
        }
    }

    private BigDecimal countPriceWithVAT(BigDecimal price) {
        return price.multiply(new BigDecimal(100)).divide(new BigDecimal(100 - vat), 0, RoundingMode.HALF_UP);
    }

    @Override
    public OrderBilling getOrderBilling(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("OrderId is null.");
        try {
            Order order = orderDao.findById(orderId);
            if (order == null) return null;

            List<BillingItem> items = new ArrayList<>();
            items.addAll(order.getListOfServices().stream()
                    .map(service -> new BillingItem(service.getDescription(), vat, service.getPrice(),
                            countPriceWithVAT(service.getPrice())))
                    .collect(Collectors.toList()));
            items.addAll(order.getListOfTires().stream()
                    .map(tire -> new BillingItem(tire.getDescription(), vat, tire.getPrice(),
                            countPriceWithVAT(tire.getPrice())))
                    .collect(Collectors.toList()));

            BigDecimal sum = items.stream()
                    .map(BillingItem::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal sumWithVAT = items.stream()
                    .map(BillingItem::getPriceWithVAT)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new OrderBilling(order.getId(), sum, sumWithVAT, items, order);

        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("Cannot create order billing.", e);
        }
    }
    @Override
    public BigDecimal calculateProfit() throws PneuservisPortalDataAccessException{
        try {
            List<Order> all = orderDao.findAll();
            BigDecimal sum = BigDecimal.ZERO;
            for (Order order : all) {
                if (order.isPaymentConfirmed()) {
                    System.out.println(sum);
                    sum = sum.add(getOrderBilling(order.getId()).getPrice());
                }
            }
            return sum;
        } catch (Throwable e) {
            throw new PneuservisPortalDataAccessException("cannot compute profit", e);
        }
    }
}
