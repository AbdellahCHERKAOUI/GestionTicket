package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.TechDTO;
import com.binarios.gestionticket.dto.response.TechResponseDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.service.PersonService;
import com.binarios.gestionticket.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class TechController {
    private final PersonService personService;
    private final TicketService ticketService;

    public TechController(PersonService personService, TicketService ticketService) {
        this.personService = personService;
        this.ticketService = ticketService;
    }


    //Update Tech
    @PutMapping("/tech/edit/{id}")
    public ResponseEntity<TechResponseDTO> updateTech(@PathVariable Long id, @RequestBody TechDTO techDTO) {
        TechResponseDTO techResponseDTO = personService.editTech(id, techDTO);
        return new ResponseEntity<>(techResponseDTO, HttpStatus.OK);
    }


}
