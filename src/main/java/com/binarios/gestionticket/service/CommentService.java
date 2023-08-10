package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.CommentRequestDTO;
import com.binarios.gestionticket.dto.response.CommentResponseDTO;
import com.binarios.gestionticket.entities.Comment;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Ticket;
import com.binarios.gestionticket.repositories.CommentRepository;
import com.binarios.gestionticket.repositories.PersonRepository;
import com.binarios.gestionticket.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final PersonRepository personRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          TicketRepository ticketRepository,
                          PersonRepository personRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.personRepository = personRepository;
    }

    public CommentResponseDTO writeComment(CommentRequestDTO requestDTO) {
        // Fetch the ticket and person entities based on their IDs from the database
        Ticket ticket = ticketRepository.findById(requestDTO.getTicket()).orElse(null);
        Person person = personRepository.findById(requestDTO.getPerson()).orElse(null);

        // Handle the case when the ticket or person doesn't exist in the database
        if (ticket == null || person == null) {
            // You can throw an exception or return an error response here
            return null;
        }

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
        Comment commentToUpdate = commentRepository.findById(commentId).orElse(null);

        // Handle the case when the comment doesn't exist in the database
        if (commentToUpdate == null) {
            // Return an error response if the comment does not exist
            return new CommentResponseDTO();
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



//supprimer un Commentaire
    public void deleteComment(Long commentId) throws Exception{

        commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("there is no comment with this id " +  commentId));
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
    public List<CommentResponseDTO> getCommentsByTicketId(Long ticketId) throws  Exception{
        List<Comment> comments = commentRepository.findByTicketId(ticketId);

        if (comments.isEmpty()){
            throw new Exception("there is no comment");
        }

        List<CommentResponseDTO> commentResponseDTOS = new ArrayList<>();

       for (Comment comment: comments){
           commentResponseDTOS.add(createResponseDTO(comment));
       }
       return commentResponseDTOS;
    }


}

