package com.nisuev.dips.services;

import com.nisuev.dips.exceptions.PersonNotFoundException;
import com.nisuev.dips.repositories.interfaces.IPersonRepository;
import com.nisuev.dips.services.interfaces.IPersonService;
import com.nisuev.dips.services.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class PersonService implements IPersonService {
    private final IPersonRepository personRepository;

    @Override
    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    @Transactional
    public Long createPerson(Person person) {
        Person saved = personRepository.createPerson(person);
        return saved.getId();
    }

    @Override
    @Transactional
    public Person updatePerson(Long id, Person person) {
        Person update_person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        if (person.getName() != null) {
            update_person.setName(person.getName());
        }
        if (person.getAge() != null) {
            update_person.setAge(person.getAge());
        }
        if (person.getAddress() != null) {
            update_person.setAddress(person.getAddress());
        }
        if (person.getWork() != null) {
            update_person.setWork(person.getWork());
        }

        return personRepository.updatePerson(update_person);
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException(id);
        }
        personRepository.deleteById(id);
    }
}