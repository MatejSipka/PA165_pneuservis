/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.dao.TireDAO;
import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Matej Sipka
 */
@org.springframework.stereotype.Service
@Transactional
public class TireServiceImpl implements TireService {

    @Autowired
    private TireDAO tireDAO;

    @Override
    public Tire create(Tire tire) throws IllegalArgumentException {
        try {
            return tireDAO.create(tire);
        } catch (ConstraintViolationException | IllegalArgumentException | NullPointerException | PersistenceException e) {
            throw new PneuservisPortalDataAccessException("Cannot create tire", e);
        }
    }

    @Override
    public Tire update(Tire tire) {
        try {
            return tireDAO.update(tire);
        } catch (ConstraintViolationException | PersistenceException | NullPointerException | IllegalArgumentException ex) {
            throw new PneuservisPortalDataAccessException("cannot update tire", ex);
        }
    }

    @Override
    public void delete(Tire tire) {
        try {
            tireDAO.delete(tire);
        } catch (IllegalArgumentException | PersistenceException | NullPointerException ex) {
            throw new PneuservisPortalDataAccessException("cannot delete tire", ex);
        }
    }

    @Override
    public Tire findById(Long id) {
        try {
            return tireDAO.findById(id);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tire by id", ex);
        }
    }

    @Override
    public List<Tire> findAll() {
        try {
            return tireDAO.findAll();
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tires", ex);
        }
    }

    @Override
    public List<Tire> findByCatalogNumber(int catalogNumber) {
        try {
            return tireDAO.findByCatalogNumber(catalogNumber);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tires by CN", ex);
        }
    }

    @Override
    public List<Tire> findByProfile(int profile) {
        try {
            return tireDAO.findByProfile(profile);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tires by profile", ex);
        }
    }

    @Override
    public List<Tire> findByDiameter(int diameter) {
        try {
            return tireDAO.findByDiameter(diameter);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tire by diameter", ex);
        }
    }

    @Override
    public List<Tire> findBySize(int size) {
        try {
            return tireDAO.findBySize(size);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tires by size", ex);
        }
    }

    @Override
    public List<Tire> findByType(TireType type) {
        try {
            return tireDAO.findByType(type);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tires by type", ex);
        }
    }

    @Override
    public List<Tire> findByManufacturer(TireManufacturer manufacturer) {
        try {
            return tireDAO.findByManufacturer(manufacturer);
        } catch (IllegalArgumentException | PersistenceException ex) {
            throw new PneuservisPortalDataAccessException("cannot find tires by manufacturer", ex);
        }
    }
}
