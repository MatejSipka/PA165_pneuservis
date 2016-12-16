package cz.fi.muni.pa165.pneuservis.rest;

import cz.fi.muni.pa165.pneuservis.dto.TireDTO;
import cz.fi.muni.pa165.pneuservis.facade.TireFacade;
import cz.fi.muni.pa165.pneuservis.rest.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Matej Sipka on 16.12.2016.
 */
@RestController
@RequestMapping("/pa165/rest/tires")
public class TiersRestController {

    @Inject
    private TireFacade tireFacade;

    @RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TireDTO> get() {
        return tireFacade.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final TireDTO get(@PathVariable long id) {
        TireDTO result = tireFacade.findById(id);
        if (result == null) {
            throw new BadRequestException();
        }
        return result;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<TireDTO> post(@RequestBody TireDTO tire) {
        tireFacade.create(tire);
        return new ResponseEntity<>(tire, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE)
    public final ResponseEntity delete(@PathVariable long id) {
        TireDTO result = tireFacade.findById(id);
        if (result == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        tireFacade.delete(result);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            headers = "Accept=application/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<TireDTO> put(@PathVariable long id, @RequestBody TireDTO tire) {
        TireDTO result = tireFacade.findById(id);
        if (result == null) {
            // IF WE ASSUME THAT PUT IS ONLY FOR THE UPDATE (NOT CREATE)
            throw new BadRequestException();
        }

        tire.setId(id);
        tireFacade.update(tire);

        return new ResponseEntity(tireFacade.findById(id), HttpStatus.CREATED);
    }
}
