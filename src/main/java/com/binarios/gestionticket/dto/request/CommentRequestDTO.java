package com.binarios.gestionticket.dto.request;

public class CommentRequestDTO {
    private String content;
    private Long ticket;


    public CommentRequestDTO(String content, Long ticket) {
        this.content = content;
        this.ticket = ticket;
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

}
