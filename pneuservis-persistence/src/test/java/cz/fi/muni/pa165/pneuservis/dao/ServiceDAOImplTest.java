package cz.fi.muni.pa165.pneuservis.dao;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cz.fi.muni.pa165.pneuservis.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.pneuservis.dao.ServiceDAO;
import cz.fi.muni.pa165.pneuservis.entity.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author Ivan Moscovic
 */

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ServiceDAOImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager entityManager;

    @Autowired
    private ServiceDAO serviceDao;

    private Services service1;
    private Services service2;

    @BeforeMethod
    private void init(){

        service1 = new Services();
        service2 = new Services();

        service1.setDuration(5);
        service1.setNameOfService("Change of gear");
        service1.setOwnParts(false);
        service1.setPrice(BigDecimal.valueOf(1200));
        service1.setTypeOfCar("BMW");

        service2.setDuration(2);
        service2.setNameOfService("Change of oil");
        service2.setOwnParts(true);
        service2.setPrice(BigDecimal.valueOf(200));
        service2.setTypeOfCar("Audi");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createWithNullTest() {
        serviceDao.create(null);
    }

    @Test
    public void createServiceWithAssignedIdTest(){
        serviceDao.create(service1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createServiceWithWrongDurationTest(){
        service1.setDuration(-5);
        serviceDao.create(service1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createServiceWithNullNameTest(){
        service1.setNameOfService(null);
        serviceDao.create(service1);
    }

    @Test
    public void updateServiceTest(){
        serviceDao.create(service1);
        serviceDao.create(service2);

        service1.setNameOfService("something new");
        serviceDao.update(service1);
        Assert.assertEquals(serviceDao.findById(service1.getId()).getNameOfService(), "something new");

        service1.setDuration(8);
        serviceDao.update(service1);
        Assert.assertEquals(serviceDao.findById(service1.getId()).getDuration(), 8);

        service1.setOwnParts(true);
        serviceDao.update(service1);
        Assert.assertEquals(serviceDao.findById(service1.getId()).hasOwnParts(), true);

        assertDeepEquals(serviceDao.findById(service2.getId()) ,service2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateServiceWithNullReference() {
        serviceDao.create(service1);
        serviceDao.update(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateServiceWithWrogDuration() {
        serviceDao.create(service1);
        service1 = serviceDao.findById(service1.getId());
        service1.setDuration(-5);
        serviceDao.update(service1);
    }



    @Test
    public void findByIdTest(){
        serviceDao.create(service1);
        serviceDao.create(service2);
        Services found = serviceDao.findById(service1.getId());
        Assert.assertEquals(found, service1);
        assertDeepEquals(found, service1);
    }

    @Test
    public void findByNonexistingIdTest(){
        Assert.assertNull(serviceDao.findById(new Long(1)));
    }

    @Test
    public void findAllTestNull() {
        Assert.assertEquals(serviceDao.findAllServices().size(), 0);
    }


    @Test
    public void findByNameTest(){
        serviceDao.create(service1);
        serviceDao.create(service2);
        List<Services> found = serviceDao.findByName("Change of oil");
        List<Services> found2 = serviceDao.findByName("do not exist");
        Assert.assertEquals(found.size(), 1);
        assertDeepEquals(found.get(0), service2);
        Assert.assertEquals(found2.size(), 0);
    }

    @Test
    public void findAllTest(){
        serviceDao.create(service1);
        serviceDao.create(service2);
        List<Services> services2 = serviceDao.findAllServices();
        Assert.assertEquals(services2.size(), 2);
        assertDeepEquals(services2.get(0), service1);
        assertDeepEquals(services2.get(1), service2);
    }

    @Test
    public void deleteTest(){
        serviceDao.create(service1);
        serviceDao.create(service2);
        Assert.assertNotNull(serviceDao.findById(service1.getId()));
        serviceDao.delete(service1);
        Assert.assertNull(serviceDao.findById(service1.getId()));
        Assert.assertNotNull(serviceDao.findById(service2.getId()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullService() {
        serviceDao.delete(null);
    }

    private void assertDeepEquals(Services ser1, Services ser2){
        Assert.assertEquals(ser1.getDuration(), ser2.getDuration());
        Assert.assertEquals(ser1.getId(), ser2.getId());
        Assert.assertEquals(ser1.getNameOfService(), ser2.getNameOfService());
        Assert.assertEquals(ser1.hasOwnParts(), ser2.hasOwnParts());
    }


}
