package com.nisuev.dips.repositories.entities;

import com.nisuev.dips.services.models.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;
    @NotNull
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Column(nullable = false, columnDefinition = "int check (age >= 0)")
    private int age;
    @NotNull
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String work;

    public Person toPerson() {
        return Person.builder()
                .id(id)
                .name(name)
                .age(age)
                .address(address)
                .work(work)
                .build();
    }

    public static PersonEntity fromPerson(Person person) {
        String work = person.getWork() == null ? "" : person.getWork();
        return new PersonEntity(
                person.getId(),
                person.getName(),
                person.getAge(),
                person.getAddress(),
                work
        );
    }
}