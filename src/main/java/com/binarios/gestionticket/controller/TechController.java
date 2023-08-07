package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.TechDTO;
import com.binarios.gestionticket.dto.response.TechResponseDTO;
import com.binarios.gestionticket.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TechController {
    private final PersonService personService;

    public TechController(PersonService personService) {
        this.personService = personService;
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
