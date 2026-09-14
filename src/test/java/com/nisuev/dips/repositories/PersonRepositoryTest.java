package com.nisuev.dips.repositories;

import com.nisuev.dips.repositories.entities.PersonEntity;
import com.nisuev.dips.repositories.jpa.JpaPersonRepository;
import com.nisuev.dips.services.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class PersonRepositoryTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:13")
                    .withDatabaseName("persons")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private JpaPersonRepository jpaPersonRepository;

    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new PersonRepository(jpaPersonRepository);
        jpaPersonRepository.deleteAll();
    }

    @Test
    void createPersonPersistsAndFindsPerson() {
        Person created = personRepository.createPerson(
                Person.builder().name("Иван").age(30).address("Москва").work("Завод").build());

        Optional<Person> found = personRepository.findById(created.getId());

        assertThat(created.getId()).isNotNull();
        assertThat(found).isPresent().get()
                .extracting(Person::getId, Person::getName, Person::getAge,
                        Person::getAddress, Person::getWork)
                .containsExactly(created.getId(), "Иван", 30, "Москва", "Завод");
    }

    @Test
    void deleteByIdRemovesPerson() {
        PersonEntity entity = jpaPersonRepository.save(PersonEntity.builder()
                .name("Иван").age(25).address("Москва").work("Завод").build());

        personRepository.deleteById(entity.getId());

        assertThat(personRepository.existsById(entity.getId())).isFalse();
        assertThat(personRepository.findById(entity.getId())).isEmpty();
    }
}
