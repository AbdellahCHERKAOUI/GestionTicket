package com.binarios.gestionticket.dto.request;

import com.binarios.gestionticket.enums.TicketStatus;

public class TicketStatusUpdateDTO {
    private Long ticketId;
    private TicketStatus newStatus;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public TicketStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(TicketStatus newStatus) {
        this.newStatus = newStatus;
    }
}
