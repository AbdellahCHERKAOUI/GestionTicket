package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.TicketDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Ticket;
import com.binarios.gestionticket.enums.PersonRole;
import com.binarios.gestionticket.enums.TicketStatus;
import com.binarios.gestionticket.repositories.PersonRepository;
import com.binarios.gestionticket.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AttachmentService attachmentService;

    private final PersonRepository personRepository;


    public TicketService(TicketRepository ticketRepository, AttachmentService attachmentService, PersonRepository personRepository) {
        this.ticketRepository = ticketRepository;
        this.attachmentService = attachmentService;
        this.personRepository = personRepository;
    }

    public TicketResponseDTO saveTicket(TicketDTO ticketDTO, MultipartFile file) {

        Ticket ticket = new Ticket();
        ticket.setName(ticketDTO.getName());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(TicketStatus.CLOSED);
        Optional<Person> client = personRepository.findById(ticketDTO.getClient_id());
        //Optional<Person> assignedTech = personRepository.findById(ticketDTO.getAssignedTech_id());

        Optional<Person> admin = personRepository.findByRole(PersonRole.ADMIN);

        ticket.setClient(client.orElse(null));
        //ticket.setAssignedTech(assignedTech.orElse(null));wha
        ticket.setAdmin(admin.orElse(null));


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

    public TicketResponseDTO editTicket(Long id, TicketDTO ticketDTO, MultipartFile file) throws Exception {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new Exception("There is no ticket with this id"));

        ticket.setName(ticketDTO.getName());
        ticket.setDescription(ticketDTO.getDescription());
        //ticket.setStatus(ticketDTO.getStatus());

        Optional<Person> client = personRepository.findById(ticketDTO.getClient_id());
        //Optional<Person> admin = personRepository.findById(ticketDTO.getAdmin_id());

        ticket.setClient(client.orElse(null));
        //ticket.setAdmin(admin.orElse(null));

        // Save the attachment if it exists
        if (!file.isEmpty()) {
            Attachment attachment = attachmentService.saveFile(file);
            ticket.getAttachments().add(attachment);
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        // Create a new TicketResponseDTO and set the fields
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

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    public TicketResponseDTO assignTicket(Long ticketId, Long techId) throws Exception{
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        Person tech = personRepository.findById(techId).orElse(null);
        if (ticket == null){
            throw new Exception("There is now ticket with this id");
        }
        if (tech == null || !tech.getRole().name().equals(PersonRole.TECH.name())){
            throw new Exception("There is now tech with this id");
        }

        ticket.setAssignedTech(tech);
        ticketRepository.save(ticket);

        // Create a new TicketResponseDTO and set the fields to give back in response
        TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
        createdTicketDTO.setId(ticket.getId());
        createdTicketDTO.setName(ticket.getName());
        createdTicketDTO.setDescription(ticket.getDescription());
        createdTicketDTO.setStatus(ticket.getStatus());
        createdTicketDTO.setAdmin(ticket.getAdmin());
        createdTicketDTO.setAssignedTech(ticket.getAssignedTech());
        createdTicketDTO.setClient(ticket.getClient());
        createdTicketDTO.setAttachments(ticket.getAttachments());

        return createdTicketDTO;
    }
}
