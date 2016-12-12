package cz.fi.muni.pa165.pneuservis.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Maros Staurovsky
 */
@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
public class NotModifiedException extends RuntimeException{
}
