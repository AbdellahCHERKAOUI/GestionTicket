package com.binarios.gestionticket.dto.request;

public class CommentRequestDTO {
    private String content;


    public CommentRequestDTO() {
    }

    public CommentRequestDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
