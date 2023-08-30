package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.TicketDTO;
import com.binarios.gestionticket.dto.request.TicketStatusUpdateDTO;
import com.binarios.gestionticket.dto.response.CommentResponseDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.service.CommentService;
import com.binarios.gestionticket.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final CommentService commentService;


    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestParam("file") MultipartFile file, @RequestPart TicketDTO ticketDTO) {
        return new ResponseEntity<>(ticketService.saveTicket(ticketDTO, file), HttpStatus.CREATED);
    }


    @GetMapping(value = "/tickets")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Collection<TicketResponseDTO>> showAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    //Update a ticket
    //Here we can update just the -name- and the -description- of the ticket
    @PutMapping(value = "/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public ResponseEntity<TicketResponseDTO> updateTicket(@RequestParam("file") MultipartFile file, @RequestPart TicketDTO ticketDTO, @PathVariable(name = "id") Long id) {
        TicketResponseDTO ticketResponseDTO = ticketService.editTicket(id, ticketDTO, file);
        return new ResponseEntity<>(ticketResponseDTO, HttpStatus.OK);
    }

    //Delete a ticket
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>("Ticket  number " + id + " deleted successfully", HttpStatus.OK);
    }

    //Assign ticket
    @PutMapping(value = "/assign/{ticketId}/{techId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<TicketResponseDTO> assignTicket(@PathVariable(name = "ticketId") Long ticketId, @PathVariable(name = "techId") Long techId) {
        TicketResponseDTO ticketResponseDTO = ticketService.assignTicket(ticketId, techId);
        return new ResponseEntity<>(ticketResponseDTO, HttpStatus.OK);
    }

    //Get the attachments of a certain ticket
    @GetMapping("/{ticketId}/attachments")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'TECH')")
    public ResponseEntity<List<Attachment>> getAttachmentsByTicketId(@PathVariable("ticketId") Long ticketId) {
        List<Attachment> attachments = ticketService.getAttachmentsByTicketId(ticketId);
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    //updateTicketStatus
    @PostMapping("/{ticketId}/updateStatus")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TECH')")
    public ResponseEntity<TicketResponseDTO> updateTicketStatus(@PathVariable Long ticketId, @RequestBody TicketStatusUpdateDTO updateDTO) {

        TicketResponseDTO responseDTO = ticketService.updateTicketStatus(ticketId, updateDTO.getNewStatus());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}/comments")
    @PreAuthorize("hasAnyAuthority('ADMIN','TECH', 'CLIENT')")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTicketId(@PathVariable Long ticketId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByTicketId(ticketId);
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }
}



