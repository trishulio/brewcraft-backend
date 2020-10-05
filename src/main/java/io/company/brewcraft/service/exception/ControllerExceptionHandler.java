package io.company.brewcraft.service.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { EntityNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return message;
    }

    @ExceptionHandler(value = { DuplicateKeyException.class })
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse runtimeException(DuplicateKeyException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return message;
    }

    @ExceptionHandler(value = { RuntimeException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse runtimeException(RuntimeException e, HttpServletRequest request) {
        ErrorResponse message = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return message;
    }
}