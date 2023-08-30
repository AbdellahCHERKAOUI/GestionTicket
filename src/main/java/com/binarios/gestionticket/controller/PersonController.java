package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.UpdatePasswordDTO;
import com.binarios.gestionticket.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;


    @PostMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TECH', 'CLIENT')")
    public ResponseEntity<String> changePassword(@RequestBody UpdatePasswordDTO requestDTO) throws Exception {
        personService.changePassword(requestDTO);
        return new ResponseEntity<>("The password has been changed successfully", HttpStatus.OK);
    }
}
