/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.entity.Order;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Jaroslav Bonco
 */
@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Order order) throws IllegalArgumentException {
        if (order == null
                || order.getClientId() == null) {
            throw new IllegalArgumentException("Order must have a clientID, some ordered products and the price cannot be negative");
        }
        em.persist(order);
    }

    @Override
    public void delete(Order order) throws IllegalArgumentException {
        if (order == null) {
            throw new IllegalArgumentException("Cannot delete null order");
        }
        if (order.getId() == null) throw new IllegalArgumentException("Cannot delete order with null id.");
        em.remove(em.contains(order) ? order : em.merge(order));
    }

    @Override
    public void update(Order order) throws IllegalArgumentException {
        if (order == null
                || order.getClientId() == null) {
            throw new IllegalArgumentException("Order must have a clientID, an overall price(cannot be negative number) and some ordered products");
        }
        em.merge(order);
    }

    @Override
    public Order findById(long id) {
        return em.find(Order.class, id);
    }

    @Override
    public List<Order> findByClientId(long clientId) {
        return em.createQuery("SELECT order FROM Order order WHERE order.clientId = :clientId", Order.class)
                .setParameter("clientId", clientId).getResultList();
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT order FROM Order order", Order.class).getResultList();
    }
}
