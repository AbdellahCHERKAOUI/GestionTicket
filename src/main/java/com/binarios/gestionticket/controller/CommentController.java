package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.request.CommentRequestDTO;
import com.binarios.gestionticket.dto.response.CommentResponseDTO;
import com.binarios.gestionticket.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('TECH', 'CLIENT')")
    public ResponseEntity<CommentResponseDTO> writeComment(@RequestBody CommentRequestDTO requestDTO) {
        CommentResponseDTO responseDTO = commentService.writeComment(requestDTO);
        if (responseDTO != null) {
            return ResponseEntity.ok(responseDTO);
        } else {
            // Handle the case when the comment creation failed (e.g., invalid ticketId or personId)
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{commentId}")
    @PreAuthorize("hasAnyAuthority('TECH', 'CLIENT')")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDTO requestDTO) {

        return new ResponseEntity<>(commentService.updateComment(commentId, requestDTO), HttpStatus.OK);
    }


    //Delete comment
    @DeleteMapping("/delete/{commentId}")
    @PreAuthorize("hasAnyAuthority('TECH', 'CLIENT')")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("The comment number" + commentId + " deleted successfully", HttpStatus.OK);
    }


}