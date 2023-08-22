package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.*;
import com.binarios.gestionticket.dto.response.CommentResponseDTO;
import com.binarios.gestionticket.dto.response.GroupResponseDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.entities.Group;
import com.binarios.gestionticket.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

//@RestController
//@RequestMapping("/api")
@AllArgsConstructor
public class CommonController {

    private final TicketService ticketService;
    private final AttachmentService attachmentService;
    private final CommentService commentService;
    private final GroupService groupService;
    private final PersonService personService;


//    //(Admin,Tech,Client) can update his password
//    @PutMapping("/{personId}/change-password")
//    public ResponseEntity<String> changePassword(@PathVariable Long personId, @RequestBody UpdatePasswordDTO requestDTO) {
//
//        try {
//            personService.changePassword(personId, requestDTO);
//            return ResponseEntity.ok("Password changed successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//
//    //Get all tickets
//    //Admin will get his tickets
//    //Client will get his created tickets
//    //Tech will get his assigned tickets
//    @GetMapping("/person/{personId}/tickets")
//    public ResponseEntity<Collection<TicketResponseDTO>> getCreatedTickets(@PathVariable("personId") Long personId) throws Exception {
//        Collection<TicketResponseDTO> ticketResponseDTOS = ticketService.getCreatedTickets(personId);
//        return new ResponseEntity<>(ticketResponseDTOS, HttpStatus.OK);
//    }
//
//    //Add an attachment to a ticket
//    //Client can add an attachment to a ticket
//    //tech con add an attachment to a ticket
//    @PostMapping("/attachment/addToTicket/{ticketId}")
//    private ResponseEntity<String> fileStoringHandler(@RequestParam("file") MultipartFile file, @PathVariable("ticketId") Long ticketId) throws Exception {
//
//        attachmentService.addFileToTicket(file, ticketId);
//
//        return new ResponseEntity<>("The file has been saved", HttpStatus.CREATED);
//    }
//
//    //(Admin,Tech,Client) can add a comment
//    @PostMapping("/comments/create")
//    public ResponseEntity<CommentResponseDTO> writeComment(@RequestBody CommentRequestDTO requestDTO) {
//        CommentResponseDTO responseDTO = commentService.writeComment(requestDTO);
//        if (responseDTO != null) {
//            return ResponseEntity.ok(responseDTO);
//        } else {
//            // Handle the case when the comment creation failed (e.g., invalid ticketId or personId)
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    //(Admin,Tech,Client) can see the comments of each other
//    @PutMapping("/comments/{commentId}")
//    public ResponseEntity<String> updateComment(@PathVariable Long commentId,
//                                                @RequestBody CommentRequestDTO requestDTO) {
//        String responseMessage = String.valueOf(commentService.updateComment(commentId, requestDTO));
//        if (responseMessage.startsWith("Error")) {
//            // Return a 404 Not Found status for error messages
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
//        } else {
//            // Return a 200 OK status for successful updates
//            return ResponseEntity.ok(responseMessage);
//        }
//    }
//
//    //(Admin,Tech,Client) can see the comments of a ticket
//    @GetMapping("/tickets/{ticketId}/comments")
//    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTicketId(@PathVariable Long ticketId) throws Exception {
//        List<CommentResponseDTO> comments = commentService.getCommentsByTicketId(ticketId);
//        return ResponseEntity.ok(comments);
//
//    }
//
//    //(Admin,Tech,Client) can see the groups
//    @GetMapping("/groups")
//    public ResponseEntity<Collection<Group>> showGroups() {
//        return new ResponseEntity<>(groupService.allGroups(), HttpStatus.OK);
//    }
//
//    //Get Group by id
//    //(Admin,Tech,Client) can see group details
//    @GetMapping("/group/{id}")
//    public ResponseEntity<GroupResponseDTO> showGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
//
//        return new ResponseEntity<>(groupService.getGroupById(id), HttpStatus.OK);
//    }
//
//    //Admin and Client can update the ticket
//    //Update a ticket
//    //Here we can update just the -name- and the -description- of the ticket
//    @PutMapping(value = "/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<TicketResponseDTO> updateTicket(@RequestParam("file") MultipartFile file, @RequestPart TicketDTO ticketDTO, @PathVariable(name = "id") Long id) throws Exception {
//        TicketResponseDTO ticketResponseDTO = ticketService.editTicket(id, ticketDTO, file);
//        return new ResponseEntity<>(ticketResponseDTO, HttpStatus.OK);
//    }
//
//    //Admin and Client delete a ticket
//    //Delete a ticket
//    @DeleteMapping ("/delete/{id}")
//    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
//        ticketService.deleteTicket(id);
//        return new ResponseEntity<>("Ticket  number "+id+" deleted successfully", HttpStatus.OK);
//    }
//
//
//    //Only admin and Tech can update the status of a ticket
//    //updateTicketStatus
//    @PostMapping("/{ticketId}/updateStatus")
//    public ResponseEntity<TicketResponseDTO> updateTicketStatus(
//            @PathVariable Long ticketId,
//            @RequestBody TicketStatusUpdateDTO updateDTO) {
//        try {
//            TicketResponseDTO responseDTO = ticketService.updateTicketStatus(ticketId, updateDTO.getNewStatus());
//            return ResponseEntity.ok(responseDTO);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

}
