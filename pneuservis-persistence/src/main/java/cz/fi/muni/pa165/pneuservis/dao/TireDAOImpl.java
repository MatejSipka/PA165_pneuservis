/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.entity.Tire;
import cz.fi.muni.pa165.pneuservis.enums.TireManufacturer;
import cz.fi.muni.pa165.pneuservis.enums.TireType;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Matej Sipka
 */
@Repository
@Transactional
public class TireDAOImpl implements TireDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Tire create(Tire tire) throws IllegalArgumentException {

        if (tire == null) {
            throw new IllegalArgumentException("Tire is null null!");
        }

        if (tire.getManufacturer() == null || tire.getType() == null) {
            throw new IllegalArgumentException("Tire manufacturer or type can not be null!");
        } else if (tire.getDiameter() <= 0) {
            throw new IllegalArgumentException("Diameter must be a positive number.");
        } else {
            em.persist(tire);
            return tire;
        }
    }

    @Override
    public Tire update(Tire tire) {

        if (tire == null) {
            throw new IllegalArgumentException("Tire is null null!");
        }

        if (tire.getManufacturer() == null || tire.getType() == null) {
            throw new IllegalArgumentException("Tire manufacturer or type can not be null!");
        } else if (tire.getDiameter() <= 0) {
            throw new IllegalArgumentException("Diameter must be a positive number.");
        } else {
            em.merge(tire);
            em.flush();
            return tire;
        }
    }

    @Override
    public void delete(Tire tire) {
        if (tire == null) {
            throw new IllegalArgumentException("Tire is null null!");
        }
        em.remove(em.contains(tire) ? tire : em.merge(tire));
    }

    @Override
    public Tire findById(Long id) {
        return em.find(Tire.class, id);
    }

    @Override
    public List<Tire> findBySize(int size) {
        return em.createQuery("SELECT tire FROM Tire tire WHERE tire.tireSize = :size",
                Tire.class).setParameter("size", size).getResultList();
    }

    @Override
    public List<Tire> findAll() {
        return em.createQuery("SELECT tire FROM Tire tire", Tire.class)
                .getResultList();
    }

    @Override
    public List<Tire> findByManufacturer(TireManufacturer manufacturer) {
//        String manufacturerName = manufacturer.name();
        return em.createQuery("SELECT tire FROM Tire tire WHERE tire.manufacturer = :name",
                Tire.class).setParameter("name", manufacturer).getResultList();
    }

    @Override
    public List<Tire> findByType(TireType type) {
        return em.createQuery("SELECT tire FROM Tire tire WHERE tire.manufacturer = :type",
                Tire.class).setParameter("type", type).getResultList();
    }

    @Override
    public List<Tire> findByCatalogNumber(int catalogNumber) {
        return em.createQuery("SELECT tire FROM Tire tire WHERE tire.catalogNumber = :catalogNumber",
                Tire.class).setParameter("catalogNumber", catalogNumber).getResultList();
    }

    @Override
    public List<Tire> findByDiameter(int diameter) {
        return em.createQuery("SELECT tire FROM Tire tire WHERE tire.diameter = :diameter",
                Tire.class).setParameter("diameter", diameter).getResultList();
    }

    @Override
    public List<Tire> findByProfile(int profile) {
        return em.createQuery("SELECT tire FROM Tire tire WHERE tire.profile = :profile",
                Tire.class).setParameter("profile", profile).getResultList();
    }

}
