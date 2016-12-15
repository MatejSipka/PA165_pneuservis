/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.pneuservis.dao;

import cz.fi.muni.pa165.pneuservis.entity.Services;

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
    Long create(Services service) throws IllegalArgumentException;

    /**
     * delete service from database
     * @param service - service to be deleted
     * @throws IllegalArgumentException - exception if service does not exist if database
     */
    Long delete(Services service) throws IllegalArgumentException;

    /**
     * update service
     * @param service -service object with updated attributes
     * @throws IllegalArgumentException - throws if service has some illegal attributes
     */
    Long update(Services service) throws IllegalArgumentException;

    /**
     * Find service by its ID
     * @param id of service
     * @return service with given id, if none is found, return null
     */
    Services findById(Long id);

    /**
     * Find service by its name
     * @param name of service
     * @return List of services with given name (if there are more)
     */
    List<Services> findByName(String name);

    /**
     * List all available services
     * @return List of services
     */
    List<Services> findAllServices();

}
