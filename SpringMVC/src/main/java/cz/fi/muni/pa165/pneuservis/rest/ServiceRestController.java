package cz.fi.muni.pa165.pneuservis.rest;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;
import cz.fi.muni.pa165.pneuservis.entity.Service;
import cz.fi.muni.pa165.pneuservis.facade.ServiceFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.AlreadyExistingException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Ivan Moscovic on 14.12.2016.
 */
public class ServiceRestController {

    final static Logger log =  LoggerFactory.getLogger(ServiceRestController.class);

    @Inject
    private ServiceFacade serviceFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ServiceDTO> list() {
        log.debug("rest getServices");
        return serviceFacade.findAllServices();
    }

    @RequestMapping(value = "/{name}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ServiceDTO> view(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!")
                                 @NotNull @PathVariable("name") String name) throws Exception{
        log.debug("rest service.findByName({})", name);
        return serviceFacade.findByName(name);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO view(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!")
                                 @NotNull @PathVariable("id") long id) throws Exception{
        log.debug("rest service.findById({})", id);
        ServiceDTO serviceDTO = serviceFacade.findById(id);

        if (serviceDTO != null) {
            return serviceDTO;
        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!")
                                 @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest service.delete({})", id);
        ServiceDTO serviceDTO = serviceFacade.findById(id);

        if (serviceDTO != null) {
            throw new NotFoundException();
        }
        try {
            serviceFacade.delete(serviceDTO);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO update(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!")
                                       @NotNull @PathVariable("id") long id,
                                  @RequestBody ServiceDTO serviceDTO) throws Exception {
        log.debug("rest service.update({})", id);
        serviceDTO.setId(id);
        try {
            serviceFacade.update(serviceDTO);
            return serviceFacade.findById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO create(@RequestBody ServiceDTO serviceDTO) throws Exception {
        log.debug("rest service.create()");
        try {
            return serviceFacade.findById(serviceFacade.create(serviceDTO));
        } catch (Exception ex) {
            throw new AlreadyExistingException();
        }
    }

}
