package com.binarios.gestionticket.repositories;

import com.binarios.gestionticket.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Long> {
}
