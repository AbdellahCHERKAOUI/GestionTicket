package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.CommentRequestDTO;
import com.binarios.gestionticket.dto.response.CommentResponseDTO;
import com.binarios.gestionticket.entities.Comment;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Ticket;
import com.binarios.gestionticket.enums.TicketStatus;
import com.binarios.gestionticket.exception.NoAuthorithyException;
import com.binarios.gestionticket.exception.ResourceNotFoundException;
import com.binarios.gestionticket.repositories.CommentRepository;
import com.binarios.gestionticket.repositories.PersonRepository;
import com.binarios.gestionticket.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final PersonRepository personRepository;


    public CommentResponseDTO writeComment(CommentRequestDTO requestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();


        Ticket ticket = ticketRepository.findById(requestDTO.getTicket()).orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id " + requestDTO.getTicket()));
        Person person = personRepository.findById(principal.getPerson().getId()).orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id " + principal.getPerson().getId()));


        // Create a new Comment entity and set its attributes based on the requestDTO
        Comment comment = new Comment();
        comment.setContent(requestDTO.getContent());
        comment.setTicket(ticket);
        comment.setPerson(person);

        // Save the new comment entity to the database
        Comment savedComment = commentRepository.save(comment);

        // Create a responseDTO and set its attributes based on the savedComment
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(savedComment.getId());
        responseDTO.setContent(savedComment.getContent());
        responseDTO.setTicket(savedComment.getTicket().getId());
        responseDTO.setPerson(savedComment.getPerson().getId());

        return responseDTO;
    }


    //modifier un commentaire
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO requestDTO) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("There is no comment to update"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();

        if (commentToUpdate.getPerson().getId() != principal.getPerson().getId()) {
            throw new NoAuthorithyException("You do not have the right to modify this comment");
        }

        // Update the comment's content if provided in the requestDTO
        if (requestDTO.getContent() != null) {
            commentToUpdate.setContent(requestDTO.getContent());
        }

        // Save the updated comment entity to the database
        Comment updatedComment = commentRepository.save(commentToUpdate);

        // Create and return the responseDTO with updated comment details
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(updatedComment.getId());
        responseDTO.setContent(updatedComment.getContent());
        responseDTO.setTicket(updatedComment.getTicket().getId());
        responseDTO.setPerson(updatedComment.getPerson().getId());

        return responseDTO;
    }


    //delete comment
    public void deleteComment(Long commentId) {
        Comment commentToUpdate = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("there is no comment with this id " + commentId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();

        if (commentToUpdate.getPerson().getId() != principal.getPerson().getId()) {
            throw new NoAuthorithyException("You do not have the right to delete this comment");
        }

        commentRepository.deleteById(commentId);

    }

    private CommentResponseDTO createResponseDTO(Comment comment) {
        CommentResponseDTO responseDTO = new CommentResponseDTO();
        responseDTO.setId(comment.getId());
        responseDTO.setContent(comment.getContent());
        responseDTO.setTicket(comment.getTicket().getId());
        responseDTO.setPerson(comment.getPerson().getId());

        return responseDTO;
    }

    //show comment
    public List<CommentResponseDTO> getCommentsByTicketId(Long ticketId) {
        List<CommentResponseDTO> commentResponseDTOS = commentRepository.findByTicketId(ticketId).stream().map(this::createResponseDTO).collect(Collectors.toList());

        if (commentResponseDTOS.isEmpty()) {
            throw new ResourceNotFoundException("there is no comment in this ticket");
        }

        return commentResponseDTOS;

    }


}

