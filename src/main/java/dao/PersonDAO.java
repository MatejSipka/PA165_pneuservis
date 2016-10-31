/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Person;
import java.util.List;

/**
 *
 * @author Maros Staurovsky
 */
public interface PersonDAO {
    
    /**
     * This method is used to creates a new person
     * @param person
     * @return id of newly created person
     * @throws ConstraintViolationException if there is any constraint violated
     */
    
    public void create (Person person) throws IllegalArgumentException;
    
    /**
     * This method is used to update an existing person
     * @param person with filled, edited parameters
     * @return cz.muni.pa165.persistence.entity with updated parameters
     * @throws ConstraintViolationException if there is any constraint violated
     */
    
    public void update (Person person);
    
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
    
    public Person FindById (long id);
    
    /**
     * Gets all people which exist at the time 
     * @return list of all people
     */
    
    public List<Person> FindAll ();
}
