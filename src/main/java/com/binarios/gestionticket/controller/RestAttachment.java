package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.service.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attachment")
@AllArgsConstructor
public class RestAttachment {

    private final AttachmentService attachmentService;


    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long attachmentId) {
        // Get the attachment data from the service
        byte[] attachmentData = attachmentService.getAttachmentData(attachmentId);

        // Get the original file name from the service (without any adjustments)
        String originalFileName = attachmentService.getAttachmentFileName(attachmentId);

        // Create a ByteArrayResource from the attachment data
        ByteArrayResource resource = new ByteArrayResource(attachmentData);

        // Build the response with the attachment data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", originalFileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
