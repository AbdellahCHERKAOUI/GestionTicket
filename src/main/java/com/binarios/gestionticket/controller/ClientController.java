package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.ClientDTO;
import com.binarios.gestionticket.dto.response.ClientResponseDTO;
import com.binarios.gestionticket.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ClientController {
    private final PersonService personService;

    public ClientController(PersonService personService) {
        this.personService = personService;
    }

    //Create Client
    @PostMapping("/client/create")
    public ResponseEntity<ClientResponseDTO> createTech(@RequestBody ClientDTO clientDTO) throws Exception{
        ClientResponseDTO createdClientDTO = personService.createClient(clientDTO);
        return new ResponseEntity<>(createdClientDTO, HttpStatus.CREATED);
    }

    //Create Client
    @PutMapping("/client/edit/{id}")
    public ResponseEntity<ClientResponseDTO> createTech(@RequestBody ClientDTO clientDTO, @PathVariable(name = "id") Long id) throws Exception{
        ClientResponseDTO createdClientDTO = personService.editClient(id,clientDTO);
        return new ResponseEntity<>(createdClientDTO, HttpStatus.CREATED);
    }
}
