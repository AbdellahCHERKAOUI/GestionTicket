package com.binarios.gestionticket.dto.request;

import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.enums.TicketStatus;

public class TicketDTO {


    private Long id;

    private String name;

    private String description;

    private TicketStatus status;

    private Long client_id;

    private Long assignedTech_id;

    private Long admin_id;

    public TicketDTO() {
    }

    public TicketDTO(Long id, String name, String description, TicketStatus status, Long client_id, Long assignedTech_id, Long admin_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.client_id = client_id;
        this.assignedTech_id = assignedTech_id;
        this.admin_id = admin_id;
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

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getAssignedTech_id() {
        return assignedTech_id;
    }

    public void setAssignedTech_id(Long assignedTech_id) {
        this.assignedTech_id = assignedTech_id;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }
}
