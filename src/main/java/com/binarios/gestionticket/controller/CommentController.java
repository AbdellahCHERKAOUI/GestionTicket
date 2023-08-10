package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.CommentRequestDTO;
import com.binarios.gestionticket.dto.response.CommentResponseDTO;
import com.binarios.gestionticket.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments/create")
    public ResponseEntity<CommentResponseDTO> writeComment(@RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO responseDTO = commentService.writeComment(requestDTO);
        if (responseDTO != null) {
            return ResponseEntity.ok(responseDTO);
        } else {
            // Handle the case when the comment creation failed (e.g., invalid ticketId or personId)
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId,
                                                @RequestBody CommentRequestDTO requestDTO) {
        String responseMessage = String.valueOf(commentService.updateComment(commentId, requestDTO));
        if (responseMessage.startsWith("Error")) {
            // Return a 404 Not Found status for error messages
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        } else {
            // Return a 200 OK status for successful updates
            return ResponseEntity.ok(responseMessage);
        }
    }


    //supprimer commentaire
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) throws Exception {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("comments  name " + commentId + " deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/tickets/{ticketId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTicketId(@PathVariable Long ticketId) throws Exception {
        List<CommentResponseDTO> comments = commentService.getCommentsByTicketId(ticketId);
        return ResponseEntity.ok(comments);

    }
}