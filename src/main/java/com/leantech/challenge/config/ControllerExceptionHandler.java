package com.leantech.challenge.config;

import com.leantech.challenge.exception.BusinessStateException;
import com.leantech.challenge.exception.ValidationException;
import com.leantech.challenge.pojo.api.ErrorResponseTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle validation exceptions in internal code by custom validations.
     * ValidationException: thrown in internal code by custom validations.
     * MethodArgumentTypeMismatchException: thrown when there is an invalid param on URL.
     */
    @ExceptionHandler({ValidationException.class, MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    ResponseEntity<ErrorResponseTO> validationException(final Exception e) {
        log.warn("Validation Exception: '{}'", e.getMessage());
        return badRequest().body(ErrorResponseTO.builder().status(BAD_REQUEST).title("Validation exception").detail(e.getMessage()).build());
    }

    /**
     * Handle business exceptions.
     */
    @ExceptionHandler({BusinessStateException.class})
    ResponseEntity<ErrorResponseTO> businessStateException(final BusinessStateException e) {
        log.warn("Illegal Business State Exception", e);
        return status(UNPROCESSABLE_ENTITY).body(ErrorResponseTO.builder().status(UNPROCESSABLE_ENTITY).title("Business state exception").detail(e.getMessage()).build());
    }

    /**
     * Handle entity exceptions.
     */
    @ExceptionHandler({EntityNotFoundException.class})
    ResponseEntity<ErrorResponseTO> entityNotFoundException(final EntityNotFoundException e) {
        log.error("Entity Not Found Exception: '{}'", e);
        return status(NOT_FOUND).body(ErrorResponseTO.builder().status(NOT_FOUND).title("Entity not found exception").detail(e.getMessage()).build());
    }

    /**
     * Handle unknown exceptions.
     */
    @ExceptionHandler({Exception.class})
    ResponseEntity<ErrorResponseTO> unknownException(final Exception e) {
        log.error("Unknown exception", e);
        return status(INTERNAL_SERVER_ERROR).body(ErrorResponseTO.builder().status(INTERNAL_SERVER_ERROR).title("Unknown exception").detail(e.getMessage()).build());
    }

    /**
     * Handle properties validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (
            final MethodArgumentNotValidException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final StringBuilder error = new StringBuilder().append("Failed validation: ");
        for (ObjectError fieldError : ex.getBindingResult().getAllErrors()) {
            error.append(fieldError.getDefaultMessage() + StringUtils.SPACE);
        }
        logger.error("Failed validation ", ex);
        return badRequest().body(ErrorResponseTO.builder().status(BAD_REQUEST).title("Failed validation").detail(error.toString()).build());
    }

}
