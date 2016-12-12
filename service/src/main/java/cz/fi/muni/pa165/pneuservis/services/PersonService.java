/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.entity.Person;
import java.util.List;

/**
 *
 * @author Maros Staurovsky
 */
public interface PersonService {
      
    /**
     * This method is used to creates a new person
     * @param person creates new person based on parameter
     * @throws IllegalArgumentException if there is any constraint violated
     */
    
    public Long create (Person person, String password) throws IllegalArgumentException;
    
    /**
     * This method is used to update an existing person
     * @param person with filled, edited parameters
     */
    
    public Person update (Person person);
    
    /**
     * This method is used to delete existing person
     * @param person is existing person to be deleted
     * @throws IllegalArgumentException if person is null
     */
    
    public void delete (Person person);
    
    /**
     * Finds person based on his id
     * @param id is id of the specific person
     * @return person with selected id
     */
    
    public Person findById (Long id);
    
    /**
     * Gets all people which exist at the time 
     * @return list of all people
     */
    
    public List<Person> findAll ();
    
    /**
    * Gets all people who have the same firstname
    * @param firstname is the firstname we are looking for
    * @return list of people with the same firstname
    */
    List<Person> findByFirstname(String firstname);
    
    /**
    * Gets all people who have the same surname
    * @param surname is the surname we are looking for
    * @return list of people with the same surname
    */
    List<Person> findBySurname(String surname);
    
    /**
    * Gets all people who have the same login
    * @param login is the login we are looking for
    * @return  person with the login provided
    */
    Person findPersonByLogin(String login);
    
    /**
     * Try to authenticate a person. Return true only if the hashed password matches the records.
     * @param person person to authenticate
     * @param password password string to compare against records
     * @return true if person is authenticated
     */
    boolean authenticate(Person person, String password);
}
