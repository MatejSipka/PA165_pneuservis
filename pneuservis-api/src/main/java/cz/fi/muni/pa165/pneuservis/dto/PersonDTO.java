/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dto;

import cz.fi.muni.pa165.pneuservis.enums.PersonType;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Maros Staurovsky
 */
public class PersonDTO {
    
    private Date DateOfBirth;

    
    private Long id;

   
    private PersonType type;

    
    private String firstname;

   
    private String surname;

    
    private String login;

    private String passwordHash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date DoB) {
        this.DateOfBirth = DoB;
    }

    public PersonType getPersonType() {
        return type;
    }

    public void setPersonType(PersonType type) {
        this.type = type;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.DateOfBirth);
        hash = 89 * hash + Objects.hashCode(this.login);
        hash = 89 * hash + Objects.hashCode(this.surname);
        hash = 89 * hash + Objects.hashCode(this.firstname);
        hash = 89 * hash + Objects.hashCode(this.type);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ||  !(obj instanceof PersonDTO)) return false;
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonDTO other = (PersonDTO) obj;
        if (this.type != other.getPersonType()) {
            return false;
        }
        if (!Objects.equals(this.firstname, other.getFirstname())) {
            return false;
        }
        if (!Objects.equals(this.surname, other.getSurname())) {
            return false;
        }
        if (!Objects.equals(this.DateOfBirth, other.getDateOfBirth())) {
            return false;
        }
        if (!Objects.equals(this.login, other.getLogin())) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.getPasswordHash())) {
            return false;
        }
        if (!Objects.equals(this.id, other.getId())) {
            return false;
        }
        return true;
    }

}
