package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.UpdatePasswordDTO;
import com.binarios.gestionticket.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/{personId}/change-password")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TECH', 'CLIENT')")
    public ResponseEntity<String> changePassword(@PathVariable Long personId, @RequestBody UpdatePasswordDTO requestDTO) {


        try {
            personService.changePassword(personId, requestDTO);
            return ResponseEntity.ok("Password changed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
