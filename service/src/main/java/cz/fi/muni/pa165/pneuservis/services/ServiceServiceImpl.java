package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.dao.ServiceDAO;
import cz.fi.muni.pa165.pneuservis.entity.Services;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import java.util.List;

/**
 * @author Ivan Moscovic on 23.11.2016.
 */
@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceDAO serviceDAO;

    @Override
    public Long create(Services service) {
        try {
            return serviceDAO.create(service);
        } catch (ConstraintViolationException | IllegalArgumentException | NullPointerException | PersistenceException e){
            throw new PneuservisPortalDataAccessException("Cannot create service", e);
        }
    }

    @Override
    public Long delete(Services service) {
        try {
            return serviceDAO.delete(service);
        } catch (ConstraintViolationException | IllegalArgumentException | NullPointerException | PersistenceException e){
            throw new PneuservisPortalDataAccessException("Cannot delete service", e);
        }
    }

    @Override
    public Long update(Services service) {
        try {
            return serviceDAO.update(service);
        } catch (ConstraintViolationException | IllegalArgumentException | NullPointerException | PersistenceException e){
            throw new PneuservisPortalDataAccessException("Cannot update service", e);
        }
    }

    @Override
    public Services findById(Long id) {
        try {
           return serviceDAO.findById(id);
        } catch (IllegalArgumentException | NullPointerException | PersistenceException e){
            throw new PneuservisPortalDataAccessException("Cannot find service by id", e);
        }
    }

    @Override
    public List<Services> findByName(String name) {
        try {
            return serviceDAO.findByName(name);
        } catch (IllegalArgumentException | NullPointerException | PersistenceException e){
            throw new PneuservisPortalDataAccessException("Cannot find service by name", e);
        }
    }

    @Override
    public List<Services> findAllServices() {
        try {
            return serviceDAO.findAllServices();
        } catch (IllegalArgumentException | NullPointerException | PersistenceException e){
            throw new PneuservisPortalDataAccessException("Cannot find all services", e);
        }
    }
}
