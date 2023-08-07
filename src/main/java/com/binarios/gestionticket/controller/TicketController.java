package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.TicketDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.service.AttachmentService;
import com.binarios.gestionticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;


    @Autowired
    public TicketController(TicketService ticketService, AttachmentService attachmentService) {
        this.ticketService = ticketService;
    }


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestParam("file") MultipartFile file, @RequestPart TicketDTO ticketDTO){
        return new ResponseEntity<>(ticketService.saveTicket(ticketDTO,file), HttpStatus.CREATED);
    }


    @GetMapping(value = "/tickets")
    public ResponseEntity<Collection<TicketResponseDTO>>  showAllTickets(){
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    //Update a ticket
    //Here we can update just the -name- and the -description- of the ticket
    @PutMapping(value = "/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TicketResponseDTO> updateTicket( @RequestParam("file") MultipartFile file, @RequestPart TicketDTO ticketDTO, @PathVariable(name = "id") Long id) throws Exception {
        TicketResponseDTO ticketResponseDTO = ticketService.editTicket(id, ticketDTO, file);
        return new ResponseEntity<>(ticketResponseDTO, HttpStatus.OK);
    }

    //Delete a ticket
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>("Ticket  number "+id+" deleted successfully", HttpStatus.OK);
    }

    //Assign ticket
    @PutMapping(value = "/assign/{ticketId}/{techId}")
    public ResponseEntity<TicketResponseDTO> assignTicket(@PathVariable(name = "ticketId") Long ticketId, @PathVariable(name = "techId") Long techId) throws Exception {
        TicketResponseDTO ticketResponseDTO = ticketService.assignTicket(ticketId,techId);
        return new ResponseEntity<>(ticketResponseDTO,HttpStatus.OK);
    }
}
