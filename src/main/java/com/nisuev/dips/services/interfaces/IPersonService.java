package com.nisuev.dips.services.interfaces;

import com.nisuev.dips.services.models.Person;

import java.util.List;

public interface IPersonService {
    Person getPerson(Long id);
    List<Person> getAllPersons();
    Long createPerson(Person request);
    Person updatePerson(Long id, Person request);
    void deletePerson(Long id);
}
