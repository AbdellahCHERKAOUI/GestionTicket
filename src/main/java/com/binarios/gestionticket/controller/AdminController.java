package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.ClientDTO;
import com.binarios.gestionticket.dto.request.PersonDTO;
import com.binarios.gestionticket.dto.request.TechDTO;
import com.binarios.gestionticket.dto.response.ClientResponseDTO;
import com.binarios.gestionticket.dto.response.PersonResponseDTO;
import com.binarios.gestionticket.dto.response.TechResponseDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.service.PersonService;
import com.binarios.gestionticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class AdminController {
    private final PersonService personService;
    private final TicketService ticketService;

    public AdminController(PersonService personService, TicketService ticketService) {
        this.personService = personService;
        this.ticketService = ticketService;
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

    //Create Client
    @PostMapping("/client/create")
    public ResponseEntity<ClientResponseDTO> createTech(@RequestBody ClientDTO clientDTO) throws Exception {
        ClientResponseDTO createdClientDTO = personService.createClient(clientDTO);
        return new ResponseEntity<>(createdClientDTO, HttpStatus.CREATED);
    }

    //Create Tech
    @PostMapping("/tech/create")
    public ResponseEntity<TechResponseDTO> createTech(@RequestBody TechDTO techDTO) {
        TechResponseDTO createdTechDTO = personService.createTech(techDTO);
        return new ResponseEntity<>(createdTechDTO, HttpStatus.CREATED);
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

    //Get all tickets
    @GetMapping("/person/{personId}/tickets")
    public ResponseEntity<Collection<TicketResponseDTO>> getCreatedTickets(@PathVariable("personId") Long personId) throws Exception {
        Collection<TicketResponseDTO> ticketResponseDTOS = ticketService.getCreatedTickets(personId);
        return new ResponseEntity<>(ticketResponseDTOS, HttpStatus.OK);
    }

    //Deactivate or activate an account
    @GetMapping("/person/active/{id}")
    public ResponseEntity<PersonResponseDTO> activateOrDeactivate(@PathVariable("id") Long id) throws Exception {
        PersonResponseDTO personResponseDTO = personService.activateOrDeactivate(id);
        return new ResponseEntity<>(personResponseDTO, HttpStatus.OK);
    }


}
