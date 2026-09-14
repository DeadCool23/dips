package com.nisuev.dips.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("Person with id " + id + " not found");
    }
}