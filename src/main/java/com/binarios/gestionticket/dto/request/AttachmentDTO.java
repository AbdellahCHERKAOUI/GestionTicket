package com.binarios.gestionticket.dto.request;

public class AttachmentDTO {
    private Long id;
    private String filePath;

    public AttachmentDTO() {
    }

    public AttachmentDTO(Long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
