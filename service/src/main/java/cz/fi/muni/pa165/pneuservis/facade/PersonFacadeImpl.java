/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.facade;

import cz.fi.muni.pa165.pneuservis.dto.PersonDTO;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.facade.PersonFacade;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.PersonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Maros Staurovsky
 */
@Service
@Transactional
public class PersonFacadeImpl implements PersonFacade{
    
    @Autowired
    private PersonService personService;
    
    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long create(PersonDTO personDto, String password) {
        Person personEntity = beanMappingService.mapTo(personDto, Person.class);
        return personService.create(personEntity, password);
    }

    @Override
    public PersonDTO update(PersonDTO person) {
        Person personEntity = personService.update(beanMappingService.mapTo(person, Person.class));
        return beanMappingService.mapTo(personEntity, PersonDTO.class);
    }

    @Override
    public void delete(PersonDTO person) {
        personService.delete(beanMappingService.mapTo(person, Person.class));
    }

    @Override
    public PersonDTO findById(Long id) {
        return beanMappingService.mapTo(personService.findById(id), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> findAll() {
        return beanMappingService.mapTo(personService.findAll(), PersonDTO.class);
    }

    @Override
    public List<PersonDTO> findByFirstname(String firstname) {
        List<Person> person = personService.findByFirstname(firstname);
        return (person == null) ? null : beanMappingService.mapTo(person, PersonDTO.class);
    }

    @Override
    public List<PersonDTO> findBySurname(String surname) {
        List<Person> person = personService.findBySurname(surname);
        return (person == null) ? null : beanMappingService.mapTo(person, PersonDTO.class);
    }

    @Override
    public boolean authenticate(PersonDTO person) {
        return personService.authenticate(
                personService.findById(person.getId()), person.getPasswordHash());
    }

    @Override
    public PersonDTO findPersonByLogin(String login) {
        Person person = personService.findPersonByLogin(login);
       if(person == null){
           return null;
                   }
       else{
           return beanMappingService.mapTo(person, PersonDTO.class);
       }
    }
    
}
