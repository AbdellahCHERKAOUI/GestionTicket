package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.GroupDTO;
import com.binarios.gestionticket.dto.request.PersonDTO;
import com.binarios.gestionticket.dto.request.TechDTO;
import com.binarios.gestionticket.dto.response.GroupResponseDTO;
import com.binarios.gestionticket.dto.response.PersonResponseDTO;
import com.binarios.gestionticket.dto.response.TechResponseDTO;
import com.binarios.gestionticket.entities.Group;
import com.binarios.gestionticket.service.GroupService;
import com.binarios.gestionticket.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class AdminController {
    private final GroupService groupService;
    private final PersonService personService;

    @Autowired
    public AdminController(GroupService groupService, PersonService personService) {
        this.groupService = groupService;
        this.personService = personService;
    }

    //Group (CRUD)
    @GetMapping("/group")
    public ResponseEntity<Collection<Group>> showGroups(){
        return new ResponseEntity<>(groupService.allGroups(), HttpStatus.OK);
    }

    //Get Group by id
    @GetMapping("/group/{id}")
    public ResponseEntity<GroupResponseDTO> showPeople(@PathVariable Long id,@RequestBody GroupDTO groupDTO) {

        return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
    }

    @PostMapping("/group/create")
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        GroupResponseDTO createdGroupResponseDTO = groupService.createGroup(groupDTO);
        return new ResponseEntity<>(createdGroupResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/group/edit/{id}")
    public ResponseEntity<GroupResponseDTO> createGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        GroupResponseDTO editedGroupResponseDTO = groupService.editGroup(id, groupDTO);
        return new ResponseEntity<>(editedGroupResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping ("/group/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<>("Group  number "+id+" deleted successfully", HttpStatus.OK);
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
    public ResponseEntity<PersonResponseDTO> createPerson(@RequestBody PersonDTO personDTO) {
        PersonResponseDTO createdUserDTO = personService.createUser(personDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    //Update Person
    @PutMapping("/person/edit/{id}")
    public ResponseEntity<PersonResponseDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonResponseDTO personResponseDTO = personService.editPerson(id, personDTO);
        return new ResponseEntity<>(personResponseDTO, HttpStatus.OK);
    }

    //Delete Person
    @DeleteMapping ("/person/delete/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>("User  number "+id+" deleted successfully", HttpStatus.OK);
    }

    //Create Tech
    @PostMapping("/tech/create")
    public ResponseEntity<TechResponseDTO> createTech(@RequestBody TechDTO techDTO) {
        TechResponseDTO createdTechDTO = personService.createTech(techDTO);
        return new ResponseEntity<>(createdTechDTO, HttpStatus.CREATED);
    }

    //Update Tech
    @PutMapping("/tech/edit/{id}")
    public ResponseEntity<TechResponseDTO> updateTech(@PathVariable Long id, @RequestBody TechDTO techDTO) {
        TechResponseDTO techResponseDTO = personService.editTech(id, techDTO);
        return new ResponseEntity<>(techResponseDTO, HttpStatus.OK);
    }
}
