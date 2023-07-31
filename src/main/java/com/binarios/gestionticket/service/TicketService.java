package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.TicketDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.entities.Ticket;
import com.binarios.gestionticket.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AttachmentService attachmentService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, AttachmentService attachmentService) {
        this.ticketRepository = ticketRepository;
        this.attachmentService = attachmentService;
    }

    public TicketResponseDTO saveTicket(TicketDTO ticketDTO, MultipartFile file) {

        Ticket ticket = new Ticket();
        ticket.setName(ticketDTO.getName());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setClient(ticketDTO.getClient());
        ticket.setAssignedTech(ticketDTO.getAssignedTech());
        ticket.setAdmin(ticketDTO.getAdmin());

        //Save the attachment
        Collection<Attachment> attachments = new ArrayList<>();
        attachments.add(attachmentService.saveFile(file));

        // Convert Collection to List
        List<Attachment> attachmentList = new ArrayList<>(attachments);

        //Setting the attachment field
        ticket.setAttachments(attachmentList);


        Ticket savedTicket = ticketRepository.save(ticket);

        // Create a new PersonDTO and set the ID and generated username
        TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
        createdTicketDTO.setId(savedTicket.getId());
        createdTicketDTO.setName(savedTicket.getName());
        createdTicketDTO.setDescription(savedTicket.getDescription());
        createdTicketDTO.setStatus(savedTicket.getStatus());
        createdTicketDTO.setAdmin(savedTicket.getAdmin());
        createdTicketDTO.setAssignedTech(savedTicket.getAssignedTech());
        createdTicketDTO.setClient(savedTicket.getClient());
        createdTicketDTO.setAttachments(savedTicket.getAttachments());

        return createdTicketDTO;
    }

    public Collection<TicketResponseDTO> getAllTickets() {
        Collection<Ticket> tickets = ticketRepository.findAll();
        Collection<TicketResponseDTO> ticketResponseDTOS = new ArrayList<>();

        for (Ticket ticket : tickets){
            TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
            createdTicketDTO.setId(ticket.getId());
            createdTicketDTO.setName(ticket.getName());
            createdTicketDTO.setDescription(ticket.getDescription());
            createdTicketDTO.setStatus(ticket.getStatus());
            createdTicketDTO.setClient(ticket.getClient());
            createdTicketDTO.setAssignedTech(ticket.getAssignedTech());
            createdTicketDTO.setAdmin(ticket.getAdmin());
            createdTicketDTO.setAttachments(ticket.getAttachments());
            ticketResponseDTOS.add(createdTicketDTO);
        }
        return ticketResponseDTOS;
    }
}
