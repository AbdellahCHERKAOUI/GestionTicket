package com.binarios.gestionticket.dto.request;

import com.binarios.gestionticket.entities.Person;
import com.binarios.gestionticket.enums.TicketStatus;

public class TicketDTO {


    private Long id;

    private String name;

    private String description;

    private TicketStatus status;

    private Person client;

    private Person assignedTech;

    private Person admin;

    public TicketDTO() {
    }

    public TicketDTO(Long id, String name, String description, TicketStatus status, Person client, Person assignedTech, Person admin) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.client = client;
        this.assignedTech = assignedTech;
        this.admin = admin;
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

    public Person getClient() {
        return client;
    }

    public void setClient(Person client) {
        this.client = client;
    }

    public Person getAssignedTech() {
        return assignedTech;
    }

    public void setAssignedTech(Person assignedTech) {
        this.assignedTech = assignedTech;
    }

    public Person getAdmin() {
        return admin;
    }

    public void setAdmin(Person admin) {
        this.admin = admin;
    }

}
