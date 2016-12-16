package cz.fi.muni.pa165.pneuservis.rest;

import cz.fi.muni.pa165.pneuservis.dto.ServiceDTO;
import cz.fi.muni.pa165.pneuservis.facade.ServiceFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.AlreadyExistingException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.BadRequestException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.InvalidParameterException;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Ivan Moscovic on 14.12.2016.
 */
@RestController
@RequestMapping("pa165/rest/services")
public class ServiceRestController {

    final static Logger log = LoggerFactory.getLogger(ServiceRestController.class);

    @Inject
    private ServiceFacade serviceFacade;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ServiceDTO> get() {
        log.debug("rest service.findAll()");
        return serviceFacade.findAllServices();
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<ServiceDTO> post(@RequestBody ServiceDTO serviceDTO) throws Exception {
        log.debug("rest service.create()");
        try {
            Long id = serviceFacade.create(serviceDTO);
            return new ResponseEntity<>(serviceFacade.findById(id), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new AlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ServiceDTO get(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest service.findById({})", id);
        ServiceDTO serviceDTO = serviceFacade.findById(id);
        if (serviceDTO != null) {
            return serviceDTO;
        } else {
            throw new BadRequestException();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<ServiceDTO> put(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id,
                                  @RequestBody ServiceDTO serviceDTO) throws Exception {
        log.debug("rest service.update({})", id);

        try {
            serviceDTO.setId(id);
            serviceFacade.update(serviceDTO);
            return new ResponseEntity<>(serviceFacade.findById(id), HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity delete(@Pattern(regexp = "^[0-9]+$", message = "Invalid id!") @NotNull @PathVariable("id") long id) throws Exception {
        log.debug("rest service.delete({})", id);
        ServiceDTO serviceDTO = serviceFacade.findById(id);
        if (serviceDTO == null) throw new BadRequestException();
        serviceFacade.delete(serviceDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
