package online.lcelectronics.api.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for indicating that a requested resource was not found.
 * This exception should be thrown when attempting to access a resource that does not exist.
 */
@StandardException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

}
