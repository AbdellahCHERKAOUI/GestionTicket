package com.binarios.gestionticket.service;

import com.binarios.gestionticket.dto.request.TicketDTO;
import com.binarios.gestionticket.dto.response.PersonResponseDTO;
import com.binarios.gestionticket.dto.response.TicketResponseDTO;
import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.entities.Ticket;
import com.binarios.gestionticket.enums.PersonRole;
import com.binarios.gestionticket.enums.TicketStatus;
import com.binarios.gestionticket.exception.NoAuthorithyException;
import com.binarios.gestionticket.exception.ResourceNotFoundException;
import com.binarios.gestionticket.repositories.AttachmentRepository;
import com.binarios.gestionticket.repositories.PersonRepository;
import com.binarios.gestionticket.repositories.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AttachmentService attachmentService;

    private final PersonRepository personRepository;
    private final AttachmentRepository attachmentRepository;


    public TicketService(TicketRepository ticketRepository, AttachmentService attachmentService, PersonRepository personRepository,
                         AttachmentRepository attachmentRepository) {
        this.ticketRepository = ticketRepository;
        this.attachmentService = attachmentService;
        this.personRepository = personRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public TicketResponseDTO saveTicket(TicketDTO ticketDTO, MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();
        Ticket ticket = new Ticket();
        ticket.setName(ticketDTO.getName());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setStatus(TicketStatus.CLOSED);
        personRepository.findById(principal.getPerson().getId()).orElseThrow();
        ticket.setClient(principal.getPerson());
        //Optional<Person> assignedTech = personRepository.findById(ticketDTO.getAssignedTech_id());

        Person admin = personRepository.findByRole(PersonRole.ADMIN).orElseThrow(() -> new ResourceNotFoundException("There is no admin in the app"));

        //ticket.setClient(client.orElse(null));
        //ticket.setAssignedTech(assignedTech.orElse(null));wha
        ticket.setAdmin(admin);


        //Save the attachment
        Collection<Attachment> attachments = new ArrayList<>();
        attachments.add(attachmentService.saveFile(file));

        // Convert Collection to List
        List<Attachment> attachmentList = new ArrayList<>(attachments);

        //Setting the attachment field
        ticket.setAttachments(attachmentList);


        Ticket savedTicket = ticketRepository.save(ticket);

        //add the ticket to the list of the client
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(savedTicket);
        principal.getPerson().setTickets(tickets);

        //add the ticket to the list of the admin
        admin.setTickets(tickets);

        // Create a new PersonDTO and set the ID and generated username
        TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
        createdTicketDTO.setId(savedTicket.getId());
        createdTicketDTO.setName(savedTicket.getName());
        createdTicketDTO.setDescription(savedTicket.getDescription());
        createdTicketDTO.setStatus(savedTicket.getStatus());
        createdTicketDTO.setAdmin(mapPersonToPersonResponseDTO(savedTicket.getAdmin()));
        if (Objects.nonNull(savedTicket.getAssignedTech())) {
            createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(savedTicket.getAssignedTech()));
        }
        createdTicketDTO.setClient(mapPersonToPersonResponseDTO(savedTicket.getClient()));
        createdTicketDTO.setAttachments(savedTicket.getAttachments());

        return createdTicketDTO;
    }

    public Collection<TicketResponseDTO> getAllTickets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();

        Long principalPerson = principal.getPerson().getId();

        Collection<Ticket> tickets = ticketRepository.findAll();


        List<Ticket> properList = new ArrayList<>();

        for (Ticket ticket : tickets) {
            if (ticket.getAssignedTech() != null && Objects.equals(ticket.getAssignedTech().getId(), principalPerson)) {
                properList.add(ticket);
            } else if (ticket.getClient() != null && Objects.equals(ticket.getClient().getId(), principalPerson)) {
                properList.add(ticket);
            } else if (ticket.getAdmin() != null && Objects.equals(ticket.getAdmin().getId(), principalPerson)) {
                properList.add(ticket);
            }
        }
        if (properList.isEmpty()) {
            throw new ResourceNotFoundException("There is no tickets to show.");
        }

        Collection<TicketResponseDTO> ticketResponseDTOS = new ArrayList<>();

        for (Ticket ticket : properList) {
            TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
            createdTicketDTO.setId(ticket.getId());
            createdTicketDTO.setName(ticket.getName());
            createdTicketDTO.setDescription(ticket.getDescription());
            createdTicketDTO.setStatus(ticket.getStatus());
            createdTicketDTO.setClient(mapPersonToPersonResponseDTO(ticket.getClient()));
            if (Objects.nonNull(ticket.getAssignedTech())) {
                createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
            }
            //createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
            createdTicketDTO.setAdmin(mapPersonToPersonResponseDTO(ticket.getAdmin()));
            createdTicketDTO.setAttachments(ticket.getAttachments());
            ticketResponseDTOS.add(createdTicketDTO);
        }
        return ticketResponseDTOS;
    }

    public TicketResponseDTO editTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id"));

        ticket.setName(ticketDTO.getName());
        ticket.setDescription(ticketDTO.getDescription());
        //ticket.setStatus(ticketDTO.getStatus());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyCustomUserDetails principal = (MyCustomUserDetails) authentication.getPrincipal();

        Person client = personRepository.findById(principal.getPerson().getId()).orElseThrow(() -> new ResourceNotFoundException("there is no person with this id"));
        //Optional<Person> admin = personRepository.findById(ticketDTO.getAdmin_id());

        ticket.setClient(client);
        //ticket.setAdmin(admin.orElse(null));


        Ticket savedTicket = ticketRepository.save(ticket);

        // Create a new TicketResponseDTO and set the fields
        TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
        createdTicketDTO.setId(savedTicket.getId());
        createdTicketDTO.setName(savedTicket.getName());
        createdTicketDTO.setDescription(savedTicket.getDescription());
        createdTicketDTO.setStatus(savedTicket.getStatus());
        createdTicketDTO.setAdmin(mapPersonToPersonResponseDTO(savedTicket.getAdmin()));
        if (Objects.nonNull(savedTicket.getAssignedTech())) {
            createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(savedTicket.getAssignedTech()));
        }
        //createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(savedTicket.getAssignedTech()));
        createdTicketDTO.setClient(mapPersonToPersonResponseDTO(savedTicket.getClient()));
        createdTicketDTO.setAttachments(savedTicket.getAttachments());

        return createdTicketDTO;
    }

    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id")); // Use orElse(null) to handle the case where the ticket doesn't exist


        attachmentRepository.deleteAll(ticket.getAttachments());


        ticketRepository.delete(ticket);

    }

    public TicketResponseDTO assignTicket(Long ticketId, Long techId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id " + ticketId));
        Person tech = personRepository.findById(techId).orElseThrow(() -> new ResourceNotFoundException("There is no tech with this id " + techId));
        if (!tech.getRole().equals(PersonRole.TECH)) {
            throw new NoAuthorithyException("This user that you're trying ro assign the ticket to is not a tech, try someone else.");
        }

        ticket.setAssignedTech(tech);
        ticketRepository.save(ticket);

        //add the ticket to the list of the tech
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
        tech.setTickets(tickets);

        // Create a new TicketResponseDTO and set the fields to give back in response
        TicketResponseDTO createdTicketDTO = new TicketResponseDTO();
        createdTicketDTO.setId(ticket.getId());
        createdTicketDTO.setName(ticket.getName());
        createdTicketDTO.setDescription(ticket.getDescription());
        createdTicketDTO.setStatus(ticket.getStatus());
        createdTicketDTO.setAdmin(mapPersonToPersonResponseDTO(ticket.getAdmin()));
        if (Objects.nonNull(ticket.getAssignedTech())) {
            createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
        }
        //createdTicketDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
        createdTicketDTO.setClient(mapPersonToPersonResponseDTO(ticket.getClient()));
        createdTicketDTO.setAttachments(ticket.getAttachments());

        return createdTicketDTO;
    }


    //updateTicketStatus

    public TicketResponseDTO updateTicketStatus(Long ticketId, TicketStatus newStatus) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + ticketId));

        ticket.setStatus(newStatus);
        Ticket updatedTicket = ticketRepository.save(ticket);


        // Create a new TicketResponseDTO and set the fields to return in the response
        TicketResponseDTO responseDTO = new TicketResponseDTO();
        responseDTO.setId(updatedTicket.getId());
        responseDTO.setName(updatedTicket.getName());
        responseDTO.setDescription(updatedTicket.getDescription());
        responseDTO.setStatus(updatedTicket.getStatus());
        responseDTO.setAdmin(mapPersonToPersonResponseDTO(updatedTicket.getAdmin()));
        if (Objects.nonNull(updatedTicket.getAssignedTech())) {
            responseDTO.setAssignedTech(mapPersonToPersonResponseDTO(updatedTicket.getAssignedTech()));
        }
        responseDTO.setClient(mapPersonToPersonResponseDTO(updatedTicket.getClient()));
        responseDTO.setAttachments(updatedTicket.getAttachments());

        return responseDTO;
    }

    public List<Attachment> getAttachmentsByTicketId(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id : " + ticketId));

        return ticket.getAttachments();
    }


    public Collection<TicketResponseDTO> getCreatedTickets(Long personId) throws Exception {
        Person person = personRepository.findById(personId).orElseThrow(() -> new Exception("there is no Person with the id : " + personId));
        Collection<Ticket> tickets = ticketRepository.findTicketsByPersonId(personId);
        Collection<TicketResponseDTO> ticketResponseDTOS = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
            ticketResponseDTO.setId(ticket.getId());
            ticketResponseDTO.setName(ticket.getName());
            ticketResponseDTO.setStatus(ticket.getStatus());
            ticketResponseDTO.setDescription(ticket.getDescription());
            ticketResponseDTO.setAdmin(mapPersonToPersonResponseDTO(ticket.getAdmin()));
            ticketResponseDTO.setClient(mapPersonToPersonResponseDTO(ticket.getClient()));
            if (Objects.nonNull(ticket.getAssignedTech())) {
                ticketResponseDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
            }
            //ticketResponseDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
            ticketResponseDTO.setAttachments(ticket.getAttachments());
            ticketResponseDTOS.add(ticketResponseDTO);
        }


        return ticketResponseDTOS;
    }

    private PersonResponseDTO mapPersonToPersonResponseDTO(Person person) {
        PersonResponseDTO personResponseDTO = new PersonResponseDTO();
        personResponseDTO.setId(person.getId());
        personResponseDTO.setUsername(person.getUsername());
        personResponseDTO.setRole(person.getRole().toString()); // Assuming you want to convert the enum to a string
        personResponseDTO.setEmail(person.getEmail());
        personResponseDTO.setPhoneNumber(person.getPhoneNumber());
        personResponseDTO.setBirthDate(person.getBirthDate());
        personResponseDTO.setFullName(person.getFullName());
        if (Objects.nonNull(person.getGroup())) {
            personResponseDTO.setGroup(person.getGroup().getId());
        }
        if (Objects.nonNull(person.getSpecialite())) {
            personResponseDTO.setSpecialite(person.getSpecialite());
        }
        personResponseDTO.setActive(person.isActive());

        return personResponseDTO;
    }


    public TicketResponseDTO getTicketById(Long id) {
        Ticket  ticket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There no ticket"));

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();
        ticketResponseDTO.setId(ticket.getId());
        ticketResponseDTO.setName(ticket.getName());
        ticketResponseDTO.setStatus(ticket.getStatus());
        ticketResponseDTO.setDescription(ticket.getDescription());
        ticketResponseDTO.setAdmin(mapPersonToPersonResponseDTO(ticket.getAdmin()));
        ticketResponseDTO.setClient(mapPersonToPersonResponseDTO(ticket.getClient()));
        if (Objects.nonNull(ticket.getAssignedTech())) {
            ticketResponseDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
        }
        //ticketResponseDTO.setAssignedTech(mapPersonToPersonResponseDTO(ticket.getAssignedTech()));
        ticketResponseDTO.setAttachments(ticket.getAttachments());

        return ticketResponseDTO;

    }
}