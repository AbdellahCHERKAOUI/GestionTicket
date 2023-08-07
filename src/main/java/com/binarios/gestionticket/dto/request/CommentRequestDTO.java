package com.binarios.gestionticket.dto.request;

public class CommentRequestDTO {
    private String content;
    private Long ticket;
    private Long person;

    public CommentRequestDTO(String content, Long ticket, Long person) {
            this.content = content;
        this.ticket = ticket;
        this.person = person;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }
}
