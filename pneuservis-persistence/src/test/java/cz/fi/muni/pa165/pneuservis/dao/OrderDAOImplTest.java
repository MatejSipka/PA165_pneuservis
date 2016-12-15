package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.pneuservis.entity.Order;
import cz.fi.muni.pa165.pneuservis.entity.Services;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.PaymentType;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jaroslav Bonco
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class OrderDAOImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    private OrderDAO orderDao;

    private Order order1;
    private Order order2;

    private List<Services> services;
    private List<Tire> tires;

    private Tire tire1;

    private Services service1;

    @BeforeMethod
    private void init() {

        order1 = new Order();
        order2 = new Order();

        services = new ArrayList<Services>();
        tires = new ArrayList<Tire>();

        tire1 = new Tire();
        tire1.setManufacturer(TireManufacturer.BARUM);
        tire1.setType(TireType.SUMMER);
        tire1.setDiameter(255);
        tire1.setPrice(new BigDecimal("100.0"));
        tire1.setTypeOfCar("Nákladní");

        service1 = new Services();
        service1.setDuration(50);
        service1.setNameOfService("Vymena pneu");
        service1.setPrice(new BigDecimal("100.0"));
        service1.setTypeOfCar("Osobní");

        tires.add(tire1);
        services.add(service1);

        order1.setClientId(1L);
        order1.setListOfServices(services);
        order1.setListOfTires(tires);
        order1.setNote("Please");
        order1.setPaymentConfirmed(false);
        order1.setPaymentType(PaymentType.COD);
        order1.setShipped(false);

        order2.setClientId(2L);
        order2.setListOfServices(services);
        order2.setListOfTires(tires);
        order2.setNote("Thank you");
        order1.setPaymentConfirmed(false);
        order1.setPaymentType(PaymentType.TRANSFER);
        order1.setShipped(true);
    }

    @Test
    public void findByIdTest() {
        orderDao.create(order1);
        Order o1 = orderDao.findById(order1.getId());
        Assert.assertEquals(o1, order1);
        assertOrderEquals(o1, order1);
    }

    @Test
    public void findByClientIdTest() {
        orderDao.create(order1);
        List<Order> orders = orderDao.findByClientId(1L);
        Assert.assertEquals(orders.size(), 1);
        Assert.assertEquals(order1, orders.get(0));
    }

    @Test
    public void findByNonexistingIdTest() {
        Assert.assertNull(orderDao.findById(new Long(1)));
    }

    @Test
    public void findAllTest() {
        orderDao.create(order2);
        List<Order> orders = orderDao.findAll();
        Assert.assertEquals(orders.size(), 1);
    }

    @Test
    public void updatePersonTest() {
        orderDao.create(order1);
        order1.setNote("Time to update");
        orderDao.update(order1);
        Order found = orderDao.findById(order1.getId());
        Order updated = orderDao.findById(found.getId());

        Assert.assertEquals(found.getNote(), updated.getNote());
        assertOrderEquals(found, updated);
    }

    @Test
    public void deleteTest() {
        orderDao.create(order1);
        Assert.assertNotNull(orderDao.findById(order1.getId()));
        orderDao.delete(order1);
        Assert.assertNull(orderDao.findById(order1.getId()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void clientIdNullTest() {
        order1.setClientId(null);
        orderDao.create(order1);
    }

    @Test
    public void noteNullTest() {
        order1.setNote(null);
        orderDao.create(order1);
    }

    private void assertOrderEquals(Order actual, Order expected) {
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getClientId(), expected.getClientId());
        Assert.assertEquals(actual.getListOfServices(), expected.getListOfServices());
        Assert.assertEquals(actual.getListOfTires(), expected.getListOfTires());
        Assert.assertEquals(actual.getNote(), expected.getNote());
    }
}
