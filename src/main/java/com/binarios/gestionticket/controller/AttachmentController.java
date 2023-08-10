package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.response.AttachmentResponseDTO;
import com.binarios.gestionticket.service.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    //Add an attachment to a ticket
    @PostMapping("/addToTicket/{ticketId}")
    private ResponseEntity<String> fileStoringHandler(@RequestParam("file") MultipartFile file, @PathVariable("ticketId") Long ticketId) throws Exception {

        attachmentService.addFileToTicket(file, ticketId);

        return new ResponseEntity<>("The file has been saved", HttpStatus.CREATED);
    }

    //Delete an attachment of a ticket -There is a parallel deletion from the attachment DB, and the list of the Ticket-
    @DeleteMapping("/delete/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable("attachmentId") Long attachmentId) throws Exception {
        attachmentService.deleteAttachmentById(attachmentId);
        return new ResponseEntity<>("The attachment with the id " + attachmentId + " has been deleted successfully", HttpStatus.OK);
    }

}
