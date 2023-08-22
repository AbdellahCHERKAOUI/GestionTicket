package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.TechDTO;
import com.binarios.gestionticket.dto.response.TechResponseDTO;
import com.binarios.gestionticket.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tech/")
@AllArgsConstructor
public class TechController {
    private final PersonService personService;


    //Create Tech


    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TechResponseDTO> createTech(@RequestBody TechDTO techDTO) {
        TechResponseDTO createdTechDTO = personService.createTech(techDTO);
        return new ResponseEntity<>(createdTechDTO, HttpStatus.CREATED);
    }

    //Update Tech
    @PutMapping("edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TechResponseDTO> updateTech(@PathVariable Long id, @RequestBody TechDTO techDTO) {
        TechResponseDTO techResponseDTO = personService.editTech(id, techDTO);
        return new ResponseEntity<>(techResponseDTO, HttpStatus.OK);
    }


}
