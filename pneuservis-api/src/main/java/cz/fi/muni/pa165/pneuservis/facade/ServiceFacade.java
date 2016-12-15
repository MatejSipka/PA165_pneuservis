package cz.fi.muni.pa165.pneuservis.facade;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;

import java.util.List;

/**
 * @author Ivan Moscovic on 23.11.2016.
 */
public interface ServiceFacade {

    /**
     * create new service and store in database
     * @param service - service to be stored
     * @throws IllegalArgumentException - exception if service has some illegal arguments
     */
    Long create(ServiceDTO service) throws IllegalArgumentException;

    /**
     * delete service from database
     * @param service - serviceId to be deleted
     * @throws IllegalArgumentException - exception if service does not exist if database
     */
    Long delete(ServiceDTO service) throws IllegalArgumentException;

    /**
     * update service
     * @param service -service object with updated attributes
     * @throws IllegalArgumentException - throws if service has some illegal attributes
     */
    Long update(ServiceDTO service) throws IllegalArgumentException;

    /**
     * Find service by its ID
     * @param id of service
     * @return service with given id, if none is found, return null
     */
    ServiceDTO findById(Long id);

    /**
     * Find service by its name
     * @param name of service
     * @return List of services with given name (if there are more)
     */
    List<ServiceDTO> findByName(String name);

    /**
     * List all available services
     * @return List of services
     */
    List<ServiceDTO> findAllServices();

}
