package com.nisuev.dips.services;

import com.nisuev.dips.repositories.interfaces.IPersonRepository;
import com.nisuev.dips.services.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private IPersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void getPersonReturnsPersonWhenItExists() {
        Person person = person(7L, "Иван", 30, "Москва", "Завод");
        when(personRepository.findById(7L)).thenReturn(Optional.of(person));

        assertThat(personService.getPerson(7L)).isEqualTo(person);

        verify(personRepository).findById(7L);
    }

    @Test
    void updatePersonChangesOnlyProvidedFields() {
        Person stored = person(7L, "Иван", 30, "Махачкала", "Завод");
        Person patch = Person.builder().address("Москва").build();
        Person updated = person(7L, "Иван", 30, "Москва", "Завод");
        when(personRepository.findById(7L)).thenReturn(Optional.of(stored));
        when(personRepository.updatePerson(stored)).thenReturn(updated);

        Person result = personService.updatePerson(7L, patch);

        assertThat(result).isEqualTo(updated);
        assertThat(stored.getName()).isEqualTo("Иван");
        assertThat(stored.getAddress()).isEqualTo("Москва");
        verify(personRepository).updatePerson(stored);
    }

    private static Person person(Long id, String name, int age, String address, String work) {
        return Person.builder().id(id).name(name).age(age).address(address).work(work).build();
    }
}
