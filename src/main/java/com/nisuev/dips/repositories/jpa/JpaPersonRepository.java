package com.nisuev.dips.repositories.jpa;

import com.nisuev.dips.repositories.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPersonRepository extends JpaRepository<PersonEntity, Long> {
}
