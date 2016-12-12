/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.serviceTest;

import cz.fi.muni.pa165.pneuservis.dao.PersonDAO;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.enums.PersonType;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.services.PersonService;
import cz.fi.muni.pa165.pneuservis.services.PersonServiceImpl;
import java.util.Arrays;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Maros Staurovsky
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PersonServiceImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private PersonDAO personDao;    

    @InjectMocks
    private PersonService personService = new PersonServiceImpl();

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Person person;
    private Person person1;
    private Person person2;
    private Person badPerson;
    
    @BeforeMethod
    public void prepareTest() {
        person1 = new Person();
        person2 = new Person();

        person1.setFirstname("Mary");
        person1.setSurname("Picking");
        person1.setLogin("Riding_Hood");
        person1.setPersonType(PersonType.CLIENT);
        
        person2.setFirstname("Johny");
        person2.setSurname("Found");
        person2.setLogin("Wolf");
        person2.setPersonType(PersonType.EMPLOYEE);
        
        person = new Person();
        person.setFirstname("First");
        person.setSurname("Person");
        person.setLogin("firstPerson");
        person.setPersonType(PersonType.EMPLOYEE);
    
        
        // findById
        when(personDao.findById(0L)).thenReturn(null);
        when(personDao.findById(1L)).thenReturn(person1);
        when(personDao.findById(2L)).thenReturn(person2);
        
        //registered (Person.class)
        when(personDao.create(person1)).thenReturn(1L);
        
        //findByName
        when(personDao.findByFirstname(person1.getFirstname())).thenReturn(Arrays.asList(person1));
        when(personDao.findBySurname(person2.getSurname())).thenReturn(Arrays.asList(person2));
        
        //find by login
        when(personDao.findByLogin(person2.getLogin())).thenReturn((person2));
        
        //findAll
        when(personDao.findAll()).thenReturn(Arrays.asList(person, person1, person2));
    }

    @Test
    public void createPersonTest() {
        personService.create(person, "hashPassword123");  
        verify(personDao).create(person);
    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void registerPersonBadCredentialsTest() {
        badPerson = new Person();
        badPerson.setFirstname("Bad");
        badPerson.setSurname("Person");
        when(personDao.create(badPerson)).thenThrow(IllegalArgumentException.class);

        personService.create(badPerson, "hashPassword123");

    }

    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
    public void registerPersonNullPasswordTest() {
        personService.create(person1, null);
        verify(personDao, times(0)).create(any(Person.class));
    }

    @Test
    public void getAllPersonTest() {
        Assert.assertEquals(personService.findAll().size(), 3);
        verify(personDao).findAll();
    }

    @Test
    public void findPersonByIdTest() {
        Assert.assertEquals(personService.findById(1L), person1);
        verify(personDao).findById(1L);
    }

//    @Test(expectedExceptions = PneuservisPortalDataAccessException.class)
//    public void findPersonByIdNullTest() {
//        when(personDao.findById(null)).thenThrow(PneuservisPortalDataAccessException.class);
//        
//        personService.findById(null);
//    }


    @Test
    public void findPersonByIdBadIdTest() {
        Assert.assertNull(personService.findById(4L));
        verify(personDao).findById(any(Long.class));
    }

    @Test
    public void authenticatePersonTest() {
        personService.create(person1, "hashPassword123");
        verify(personDao).create(person1);
        personService.authenticate(person1, "hashPassword123");
    }

    @Test
    public void authenticatePersonBadUsernameTest() {
        badPerson = new Person();
        badPerson.setFirstname("Bad");
        badPerson.setSurname("Person");
        badPerson.setLogin("badPerson");

        personService.create(badPerson, "badPassword");
        personService.create(person1, "hashPassword123");

        Assert.assertFalse(personService.authenticate(badPerson, "hashPassword123"));
    }

    @Test
    public void authenticatePersonBadPasswordTest() {
        personService.create(person1, "hashPassword123");
        Assert.assertFalse(personService.authenticate(person1, "badPassword"));
    }
}
