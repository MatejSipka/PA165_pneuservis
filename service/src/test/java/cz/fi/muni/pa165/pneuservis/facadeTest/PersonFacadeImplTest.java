package cz.fi.muni.pa165.pneuservis.facadeTest;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.configuration.ServiceConfiguration;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacadeImpl;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingServiceImpl;
import cz.fi.muni.pa165.pneuservis.services.PersonService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maros Staurovsky
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PersonFacadeImplTest extends AbstractTestNGSpringContextTests{
    
    @Mock
    private PersonService personService;    

    @InjectMocks
    private PersonFacade personFacade = new PersonFacadeImpl();
    

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();
    
    
    private PersonDTO person1DTO;
    private PersonDTO person2DTO;
    private List<PersonDTO> people;
    private Person person1;
    private Person person2;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void init() {
        person1 = new Person();
        person2 = new Person();

        person1.setFirstname("Mary");
        person1.setSurname("Picking");
        person1.setLogin("Riding_Hood");
        
        person2.setFirstname("Johny");
        person2.setSurname("Found");
        person2.setLogin("Wolf");
        
        person1.setId(1l);
        
        person1DTO = new PersonDTO();
        person2DTO = new PersonDTO();
        
        person1DTO.setFirstname(person1.getFirstname());
        person1DTO.setSurname(person1.getSurname());
        person1DTO.setLogin(person1.getLogin());
        person1DTO.setId(1l);
        
        person2DTO.setFirstname(person2.getFirstname());
        person2DTO.setSurname(person2.getSurname());
        person2DTO.setLogin(person2.getLogin());
        
        people = new ArrayList<>();
        people.add(person1DTO);
        people.add(person2DTO);
    }
    
    @BeforeMethod
    public void initMocksBehaviour() {
        
        // findById
        when(personService.findById(0l)).thenReturn(null);
        when(personService.findById(1l)).thenReturn(person1);
        when(personService.findById(2l)).thenReturn(person2);
        
        //register
        when(personService.create(person1, "MyPassword123")).thenReturn(1l);
        
        
        //findByName
        when(personService.findByFirstname(person1.getFirstname())).thenReturn(Arrays.asList(person1));
        when(personService.findBySurname(person2.getSurname())).thenReturn(Arrays.asList(person2));
        
        //find by login
        when(personService.findPersonByLogin(person2.getLogin())).thenReturn((person2));
        
        //authenticate
        when(personService.authenticate(person1, "MyPassword123" )).thenReturn(true);
       
        //findAll
        when(personService.findAll()).thenReturn(Arrays.asList(person1, person2));
        
    }
    
    @Test
    public void createPersonTest() {
        
        personFacade.create(person1DTO, "MyPassword123");
        verify(personService).create(any(Person.class), any(String.class));
        
    }
    
    @Test
    public void findByIdTest() {
        
        PersonDTO personId = personFacade.findById(person1.getId());
        assertNotNull(personId);
        assertEquals(person1.getId(), personId.getId());
        
    }
    
    @Test
    public void deletePersonTest() {
        
        personFacade.delete(person1DTO);
        verify(personService).delete(person1);   
    
    }
    
    @Test
    public void getAllPersonTest() {
        
        List<PersonDTO> people = personFacade.findAll();
        verify(personService).findAll();

        assertNotNull(people);
        assertEquals(people.get(0).getId(), person1.getId());
        assertEquals(people.get(1).getId(), person2.getId());
        
    }
    
    @Test
    public void findByFirstNameTest() {

        List<PersonDTO> personNames = personFacade.findByFirstname(person1.getFirstname());
        verify(personService).findByFirstname(any(String.class));

        assertNotNull(personNames);
        assertEquals(personNames.get(0).getFirstname(), person1.getFirstname());

    }


    @Test
    public void findBySureNameTest() {

        List<PersonDTO> personNames = personFacade.findBySurname(person2.getSurname());
        verify(personService).findBySurname(any(String.class));

        assertNotNull(personNames);
        assertEquals(personNames.get(0).getSurname(), person2.getSurname());

    }
    
    @Test
    public void findByLoginTest() {

        PersonDTO personLogin = personFacade.findPersonByLogin(person2.getLogin());
        verify(personService).findPersonByLogin(any(String.class));

        assertNotNull(personLogin);
        

    }

    @Test
    public void authenticateTest() {
        
        PersonDTO p = new PersonDTO();
        
        p.setId(person1.getId());
        p.setPasswordHash("MyPassword123");

        Boolean personAuth = personFacade.authenticate(p);
        verify(personService).authenticate(any(Person.class),any(String.class));

        assertTrue(personAuth);
        

    }
    
}
