package cz.fi.muni.pa165.pneuservis.dao;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import cz.fi.muni.pa165.pneuservis.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.enums.PersonType;
import cz.fi.muni.pa165.pneuservis.dao.PersonDAO;
import java.util.Date;
import java.util.Calendar;
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
/**
 *
 * @author Maros Staurovsky
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PersonDAOImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    private PersonDAO personDao;

    private Person person1;
    private Person person2;

    @BeforeMethod
    private void init(){
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995, 6, 2);
        Date date1 = calendar.getTime();
        calendar.set(1968, 10, 5);
        Date date2 = calendar.getTime();
        
        person1 = new Person();
        person2 = new Person();
        
        person1.setFirstname("John");
        person1.setSurname("Green");
        person1.setPersonType(PersonType.CLIENT);
        person1.setLogin("person.one");
        person1.setDateOfBirth(date1);

        person2.setFirstname("Sally");
        person2.setSurname("Orange");
        person2.setPersonType(PersonType.CLIENT);
        person2.setLogin("person.two");
        person2.setDateOfBirth(date2);
    }

    @Test
    public void findByIdTest(){
        personDao.create(person1);
        Person h1 = personDao.findById(person1.getId());
        Assert.assertEquals(h1, person1);
        assertPersonEquals(h1, person1);
    }

    @Test
    public void findByNonexistingIdTest(){
        Assert.assertNull(personDao.findById(new Long(1)));
    }

    @Test
    public void findAllTestNull()
    {
        Assert.assertEquals(personDao.findAll().size(), 0);
    }
    
    
    @Test
    public void findAllTest(){
        personDao.create(person1);
        personDao.create(person2);
        Assert.assertEquals(personDao.findAll().size(), 2);
    }

    @Test
    public void updatePersonTest(){
        personDao.create(person1);
        personDao.create(person2);
        Person found = personDao.findById(person1.getId());
        found.setFirstname(found.getFirstname());
        personDao.update(found);
        Person updated = personDao.findById(found.getId());

        Assert.assertEquals(found.getFirstname(), updated.getFirstname());
        assertPersonEquals(found, updated);
    }

    @Test
    public void deleteTest(){
        personDao.create(person1);
        personDao.create(person2);
        Assert.assertNotNull(personDao.findById(person1.getId()));
        personDao.delete(person1);
        Assert.assertNull(personDao.findById(person1.getId()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void firstnameNullTest(){
        person1.setFirstname(null);
        personDao.create(person1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void surnameNullTest(){
        person1.setSurname(null);
        personDao.create(person1);
    }

    private void assertPersonEquals(Person actual, Person expected){
        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getFirstname(), expected.getFirstname());
        Assert.assertEquals(actual.getSurname(), expected.getSurname());
        Assert.assertEquals(actual.getPersonType(), expected.getPersonType());
        Assert.assertEquals(actual.getLogin(), expected.getLogin());
        Assert.assertEquals(actual.getDateOfBirth(), expected.getDateOfBirth());
    }

}
