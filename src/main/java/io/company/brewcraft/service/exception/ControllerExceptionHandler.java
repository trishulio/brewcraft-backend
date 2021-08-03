package io.company.brewcraft.service.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = { EntityNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        log.debug("Entity Not Found Exception", e);
        return message;
    }

    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse constraintViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        log.debug("Data Integrity Violaton Exception", e);
        return message;
    }

    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse emptyResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        log.debug("Empty Result Exception", e);
        return message;
    }

    @ExceptionHandler(value = { ObjectOptimisticLockingFailureException.class, OptimisticLockException.class })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse objectOptimisticLockingFailureException(RuntimeException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        log.debug("Optimistic Locking Failure Exception", e);
        return message;
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.debug("Method argument not valid", e);
        
        String fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));        
        
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), fieldErrors, request.getRequestURI());
        
        return message;
    }

    @ExceptionHandler(value = { RuntimeException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse runtimeException(RuntimeException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        log.error("Runtime Exception", e);
        return message;
    }
    
    @ExceptionHandler(value = { JpaObjectRetrievalFailureException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse jpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException e, HttpServletRequest request) {        
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMostSpecificCause().getMessage(), request.getRequestURI());

        log.debug("Entity Not Found Exception", e);
        return message;
    }
    
    @ExceptionHandler(value = { IllegalArgumentException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentNotValidException(IllegalArgumentException e, HttpServletRequest request) {
        log.debug("argument not valid", e);
        
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return message;
    }
    
    @ExceptionHandler(value = { ConversionFailedException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse conversionFailedErrorResponse(ConversionFailedException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), request.getRequestURI());
        
        return message;
    }
}