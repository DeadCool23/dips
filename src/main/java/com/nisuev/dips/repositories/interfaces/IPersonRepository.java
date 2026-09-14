package com.nisuev.dips.repositories.interfaces;

import com.nisuev.dips.services.models.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository {
    boolean existsById(Long id);

    List<Person> findAll();
    Optional<Person> findById(Long id);

    Person createPerson(Person person);
    Person updatePerson(Person person);

    void deleteById(Long id);
}
