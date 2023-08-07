package com.binarios.gestionticket.dto.response;

public class CommentResponseDTO {
    private Long id;
    private String content;

    private Long ticket;

    private Long person;



    public CommentResponseDTO(){}

    public CommentResponseDTO(Long id,String message, Long createdBy, String content, Long ticket) {
        this.id = id;
        this.content = content;
        this.ticket = ticket;
        this.person=person;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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