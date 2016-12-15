/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.entity.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maros Staurovsky
 */
@Repository
@javax.transaction.Transactional
public class PersonDAOImpl implements PersonDAO{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Long create(Person person) throws IllegalArgumentException {
        if(person.getFirstname()==null || person.getSurname() == null || person.getLogin() == null)
        {
            throw new IllegalArgumentException("Person cannot have null parameter");
        }
        else
        {
            em.persist(person);
            em.flush();
            return person.getId();
        }
    }

    @Override
    public Person update(Person person) {
       return em.merge(person);
    }

    @Override
    public void delete(Person person) {
        if(person == null) {
            throw new IllegalArgumentException("Trying to delete null");
        }
        em.remove(em.contains(person) ? person : em.merge(person));
    }

    @Override
    public Person findById(Long id) {
        return em.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
         return em.createQuery("select person from Person person", Person.class)
				.getResultList();
    }

    @Override
    public List<Person> findByFirstname(String firstname) {
        return em.createQuery("select person from Person person where person.firstname = :firstname")
                .setParameter("firstname", firstname)
                .getResultList();
    }

    @Override
    public List<Person> findBySurname(String surname) {
        return em.createQuery("select person from Person person where person.surname = :surname")
                .setParameter("surname", surname)
                .getResultList();
    }

    @Override
    public Person findByLogin(String login) {
        if (login == null || login.isEmpty())
            throw new IllegalArgumentException("Cannot search for null login");
        try{
            return em.createQuery("select p from Person p where p.login = :login", Person.class)
                .setParameter("login", login).getSingleResult();
        }catch(NoResultException noResult){
            return null;
        }
    }
    
}
