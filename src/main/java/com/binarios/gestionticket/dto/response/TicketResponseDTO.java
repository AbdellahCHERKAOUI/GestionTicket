package com.binarios.gestionticket.dto.response;

import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.enums.TicketStatus;

import java.util.List;

public class TicketResponseDTO {
    private Long id;

    private String name;

    private String description;

    private TicketStatus status;

    private PersonResponseDTO client;

    private PersonResponseDTO assignedTech;

    private PersonResponseDTO admin;

    private List<Attachment> attachments;

    public TicketResponseDTO() {
    }

    public TicketResponseDTO(Long id, String name, String description, TicketStatus status, PersonResponseDTO client, PersonResponseDTO assignedTech, PersonResponseDTO admin, List<Attachment> attachments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.client = client;
        this.assignedTech = assignedTech;
        this.admin = admin;
        this.attachments = attachments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public PersonResponseDTO getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = new PersonResponseDTO();
        this.client.setId(client.getId());
        this.client.setFullName(client.getFullName());
        this.client.setEmail(client.getEmail());
        this.client.setBirthDate(client.getBirthDate());
        this.client.setRole(client.getRole().name());
        this.client.setUsername(client.getUsername());
        this.client.setPhoneNumber(client.getPhoneNumber());
    }

    public PersonResponseDTO getAssignedTech() {
        return assignedTech;
    }

    public void setAssignedTech(Person assignedTech) {
        if (assignedTech == null){
            this.assignedTech = new PersonResponseDTO();
            this.assignedTech = null;
        }
        else {
            this.assignedTech = new PersonResponseDTO();
            this.assignedTech.setId(assignedTech.getId());
            this.assignedTech.setFullName(assignedTech.getFullName());
            this.assignedTech.setEmail(assignedTech.getEmail());
            this.assignedTech.setBirthDate(assignedTech.getBirthDate());
            this.assignedTech.setRole(assignedTech.getRole().name());
            this.assignedTech.setUsername(assignedTech.getUsername());
            this.assignedTech.setPhoneNumber(assignedTech.getPhoneNumber());
            this.assignedTech.setSpecialite(assignedTech.getSpecialite());
        }
    }

    public PersonResponseDTO getAdmin() {
        return admin;
    }

    public void setAdmin(Person admin) {
        this.admin = new PersonResponseDTO();
        this.admin.setId(admin.getId());
        this.admin.setFullName(admin.getFullName());
        this.admin.setEmail(admin.getEmail());
        this.admin.setBirthDate(admin.getBirthDate());
        this.admin.setRole(admin.getRole().name());
        this.admin.setUsername(admin.getUsername());
        this.admin.setPhoneNumber(admin.getPhoneNumber());
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
