package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.PersonDTO;
import com.binarios.gestionticket.dto.response.PersonResponseDTO;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Role;
import com.binarios.gestionticket.enums.PersonRole;
import com.binarios.gestionticket.repositories.PersonRepository;
import com.binarios.gestionticket.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api/person")
@AllArgsConstructor
public class AdminController {
    private final PersonService personService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonRepository personRepository;


    @PostConstruct
    public void init() {
        boolean existed = personService.existByRole(PersonRole.ADMIN);
        if (!existed) {
            Person person = new Person();
            person.setFullName("admin");
            person.setPassword(bCryptPasswordEncoder.encode("root"));
            Set<Role> roles = new HashSet<>();
            roles.add(new Role("ADMIN"));
            person.setRoles(roles);
            person.setRole(PersonRole.ADMIN);
            person.setEmail("admin@gmail.com");
            person.setPhoneNumber("0000000");
            person.setBirthDate(LocalDate.parse("2000-11-01"));
            person.setActive(true);

            // Generate the username automatically based on fullName and a random 4-digit number
            String generatedUsername = personService.generateUsername("admin");
            person.setUsername(generatedUsername);

            personRepository.save(person);
        }

    }

    //Person (CRUD)

    //Show Users
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Collection<PersonResponseDTO>> showPeople() {

        return new ResponseEntity<>(personService.allUsers(), HttpStatus.OK);
    }

    //Get Person by id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PersonResponseDTO> showPerson(@PathVariable Long id) {

        return new ResponseEntity<>(personService.getUserById(id), HttpStatus.OK);
    }
    //Create Admin
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PersonResponseDTO> createAdmin(@RequestBody PersonDTO personDTO) throws Exception{
        PersonResponseDTO createdUserDTO = personService.createAdmin(personDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    //Update Admin
    @PutMapping("/admin/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PersonResponseDTO> updateAdmin(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonResponseDTO personResponseDTO = personService.editAdmin(id, personDTO);
        return new ResponseEntity<>(personResponseDTO, HttpStatus.OK);
    }

    //Delete Person
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>("MyCustomUserDetails  number " + id + " deleted successfully", HttpStatus.OK);
    }

    //Deactivate or activate an account
    @GetMapping("/active/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PersonResponseDTO> activateOrDeactivate(@PathVariable("id") Long id) {
        PersonResponseDTO personResponseDTO = personService.activateOrDeactivate(id);
        return new ResponseEntity<>(personResponseDTO, HttpStatus.OK);
    }


}
