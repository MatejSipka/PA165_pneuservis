/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.dao.PersonDAO;
import cz.fi.muni.pa165.pneuservis.entity.Person;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Maros Staurovsky
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    
    @Autowired
    private PersonDAO personDao;
    

    @Override
    public Long create(Person person, String password) {
        try{
            person.setPasswordHash(createHash(password));
            return personDao.create(person);
        }
        catch (ConstraintViolationException | PersistenceException | NullPointerException| IllegalArgumentException ex) {
            throw new PneuservisPortalDataAccessException("cannot create person", ex);
        }
    }
    @Override
    public Person update(Person person) {
        try {
               return personDao.update(person);
        } catch (ConstraintViolationException | PersistenceException | NullPointerException| IllegalArgumentException ex) {
            throw new PneuservisPortalDataAccessException("cannot update person", ex);
        }
    }

    @Override
    public void delete(Person person) {
        try {
            personDao.delete(person);
        } catch (IllegalArgumentException | PersistenceException | NullPointerException ex) {
            throw new PneuservisPortalDataAccessException("cannot delete person", ex);
        }
    }

    @Override
    public Person findById(Long id) {
        try {
            return personDao.findById(id);
        } catch (IllegalArgumentException | PersistenceException | NullPointerException | ConstraintViolationException ex) {
            throw new PneuservisPortalDataAccessException("cannot find person", ex);
        }
    }

    @Override
    public List<Person> findAll() {
        try {
            return personDao.findAll();
        } catch (IllegalArgumentException | PersistenceException | NullPointerException ex) {
            throw new PneuservisPortalDataAccessException("cannot find all people", ex);
        }
    }
    
    //see  https://crackstation.net/hashing-security.htm#javasourcecode
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if(password==null) return false;
        if(correctHash==null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }
    
    
    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    @Override
    public List<Person> findByFirstname(String firstname) {
        try {
            return personDao.findByFirstname(firstname);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find people", ex);
        }
    }

    @Override
    public List<Person> findBySurname(String surname) {
        try {
            return personDao.findBySurname(surname);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find people", ex);
        }
    }

    @Override
    public Person findPersonByLogin(String login) {
        return personDao.findByLogin(login);
    }

    @Override
    public boolean authenticate(Person person, String password) {
        try {
            return validatePassword(password, person.getPasswordHash());
        } catch (IllegalArgumentException | PersistenceException | NullPointerException ex) {
            throw new PneuservisPortalDataAccessException("bad auth", ex);
        }
    }
    
}
