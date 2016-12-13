/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.entity.Service;

import java.util.List;

/**
 *
 * @author Ivan Moscovic
 */
public interface ServiceDAO {

    /**
     * create new service and store in database
     * @param service - service to be stored
     * @throws IllegalArgumentException - exeption if service has some illegal arguments
     */
    Long create(Service service) throws IllegalArgumentException;

    /**
     * delete service from database
     * @param service - service to be deleted
     * @throws IllegalArgumentException - exception if service does not exist if database
     */
    Long delete(Service service) throws IllegalArgumentException;

    /**
     * update service
     * @param service -service object with updated attributes
     * @throws IllegalArgumentException - throws if service has some illegal attributes
     */
    Long update(Service service) throws IllegalArgumentException;

    /**
     * Find service by its ID
     * @param id of service
     * @return service with given id, if none is found, return null
     */
    Service findById(Long id);

    /**
     * Find service by its name
     * @param name of service
     * @return List of services with given name (if there are more)
     */
    List<Service> findByName(String name);

    /**
     * List all available services
     * @return List of services
     */
    List<Service> findAllServices();

}
