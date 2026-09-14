package com.nisuev.dips.repositories;

import com.nisuev.dips.repositories.entities.PersonEntity;
import com.nisuev.dips.repositories.interfaces.IPersonRepository;
import com.nisuev.dips.repositories.jpa.JpaPersonRepository;
import com.nisuev.dips.services.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PersonRepository implements IPersonRepository {
    private JpaPersonRepository repo;

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public List<Person> findAll() {
        return repo.findAll()
                .stream()
                .map(PersonEntity::toPerson)
                .toList();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return repo.findById(id)
                .map(PersonEntity::toPerson);
    }

    @Override
    public Person createPerson(Person person) {
        return repo.save(PersonEntity.fromPerson(person))
                .toPerson();
    }

    @Override
    public Person updatePerson(Person person) {
        return repo.save(PersonEntity.fromPerson(person))
                .toPerson();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
