package com.binarios.gestionticket.dto.response;

import jakarta.persistence.Column;

public class AttachmentResponseDTO {


    private String fileName;

    private String fileType;

    private String filePath;


    public AttachmentResponseDTO() {
    }

    public AttachmentResponseDTO(String fileName, String fileType, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
