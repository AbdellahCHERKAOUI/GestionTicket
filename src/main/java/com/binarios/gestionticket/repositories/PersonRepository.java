package com.binarios.gestionticket.repositories;

import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.enums.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByRole(PersonRole personRole);
    Optional<Person> findByEmail(String email);

}
