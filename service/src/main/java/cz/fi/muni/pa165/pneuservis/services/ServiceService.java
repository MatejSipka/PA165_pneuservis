package cz.fi.muni.pa165.pneuservis.services;

import cz.fi.muni.pa165.pneuservis.entity.Service;

import java.util.List;

/**
 * @author on 23.11.2016.
 */
public interface ServiceService {

    /**
     * create new service and store in database
     * @param service - service to be stored
     * @throws IllegalArgumentException - exception if service has some illegal arguments
     */
    Service create(Service service) throws IllegalArgumentException;

    /**
     * delete service from database
     * @param service - serviceId to be deleted
     * @throws IllegalArgumentException - exception if service does not exist if database
     */
    Service delete(Service service) throws IllegalArgumentException;

    /**
     * update service
     * @param service -service object with updated attributes
     * @throws IllegalArgumentException - throws if service has some illegal attributes
     */
    void update(Service service) throws IllegalArgumentException;

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
