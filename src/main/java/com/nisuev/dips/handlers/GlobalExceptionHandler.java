package com.nisuev.dips.handlers;

import com.nisuev.dips.controllers.responses.ErrorResponse;
import com.nisuev.dips.exceptions.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(PersonNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}