/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.entity.Service;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Ivan Moscovic
 */

@Repository
@Transactional
public class ServiceDAOImpl implements ServiceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(Service service) throws IllegalArgumentException {
        if (service == null || service.getNameOfService() == null || service.getDuration() <= 0){
            throw new IllegalArgumentException("Service must have a name and positive duration");
        }
        else {
            entityManager.persist(service);
            entityManager.flush();
            return service.getId();
        }
    }

    @Override
    public Long delete(Service service) throws IllegalArgumentException{
        if (service == null){
            throw new IllegalArgumentException("Cannot delete null service");
        }
        entityManager.remove(service);
        return service.getId();
    }

    @Override
    public Long update(Service service) throws IllegalArgumentException {
        if (service == null || service.getNameOfService() == null || service.getDuration() <= 0){
            throw new IllegalArgumentException("Service must have a name and positive duration");
        }
        else {
            entityManager.merge(service);
            return service.getId();
        }
    }

    @Override
    public Service findById(Long id) throws IllegalArgumentException {
        if (id == null){
            throw new IllegalArgumentException("id is null");
        }
        return entityManager.find(Service.class, id);
    }


    @Override
    public List<Service> findByName(String name) {
        return entityManager.createQuery("SELECT service FROM Service service WHERE service.nameOfService LIKE :name",
                Service.class).setParameter("name", name).getResultList();
    }

    @Override
    public List<Service> findAllServices() {
        return entityManager.createQuery("SELECT service FROM Service service", Service.class).getResultList();
    }
}
