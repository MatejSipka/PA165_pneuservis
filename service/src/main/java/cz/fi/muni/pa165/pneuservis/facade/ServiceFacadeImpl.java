package cz.fi.muni.pa165.pneuservis.facade;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.facade.ServiceFacade;
import cz.fi.muni.pa165.pneuservis.exception.PneuservisPortalDataAccessException;
import cz.fi.muni.pa165.pneuservis.services.BeanMappingService;
import cz.fi.muni.pa165.pneuservis.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ivan Moscovic on 23.11.2016.
 */
@org.springframework.stereotype.Service
@Transactional
public class ServiceFacadeImpl implements ServiceFacade {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void create(ServiceDTO service) {
        Service serviceEntity = beanMappingService.mapTo(service, Service.class);
        serviceService.create(serviceEntity);
    }

    @Override
    public void delete(ServiceDTO service) {
        serviceService.delete(beanMappingService.mapTo(service, Service.class));
    }

    @Override
    public void update(ServiceDTO service) {
        serviceService.update(beanMappingService.mapTo(service, Service.class));
    }

    @Override
    public ServiceDTO findById(long id) {
        return beanMappingService.mapTo(serviceService.findById(id), ServiceDTO.class);
    }

    @Override
    public List<ServiceDTO> findByName(String name) {
        List<Service> services = serviceService.findByName(name);
        if (services == null){
            return null;
        }
        else {
            return beanMappingService.mapTo(services, ServiceDTO.class);
        }
    }

    @Override
    public List<ServiceDTO> findAllServices() {
        List<Service> services = serviceService.findAllServices();
        if (services == null){
            return null;
        }
        else {
            return beanMappingService.mapTo(services, ServiceDTO.class);
        }
    }
}
