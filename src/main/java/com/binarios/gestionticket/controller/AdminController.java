package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.PersonDTO;
import com.binarios.gestionticket.dto.response.PersonResponseDTO;
import com.binarios.gestionticket.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class AdminController {
    private final PersonService personService;

    @Autowired
    public AdminController(PersonService personService) {
        this.personService = personService;
    }
    //Person (CRUD)

    //Show Users
    @GetMapping("/person")
    public ResponseEntity<Collection<PersonResponseDTO>> showPeople() {

        return new ResponseEntity<>(personService.allUsers(), HttpStatus.OK);
    }

    //Get Person by id
    @GetMapping("/person/{id}")
    public ResponseEntity<PersonResponseDTO> showPerson(@PathVariable Long id) {

        return new ResponseEntity<>(personService.getUserById(id), HttpStatus.OK);
    }

    //Create Person
    @PostMapping("/person/create")
    public ResponseEntity<PersonResponseDTO> createAdmin(@RequestBody PersonDTO personDTO) {
        PersonResponseDTO createdUserDTO = personService.createAdmin(personDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    //Update Person
    @PutMapping("/person/edit/{id}")
    public ResponseEntity<PersonResponseDTO> updateAdmin(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonResponseDTO personResponseDTO = personService.editAdmin(id, personDTO);
        return new ResponseEntity<>(personResponseDTO, HttpStatus.OK);
    }

    //Delete Person
    @DeleteMapping("/person/delete/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>("User  number " + id + " deleted successfully", HttpStatus.OK);
    }


}
