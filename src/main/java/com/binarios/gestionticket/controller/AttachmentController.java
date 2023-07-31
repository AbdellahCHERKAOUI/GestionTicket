package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.dto.response.AttachmentResponseDTO;
import com.binarios.gestionticket.service.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/create")
    private ResponseEntity<String> fileStoringHandler(@RequestParam("file") MultipartFile file) {

        attachmentService.saveFile(file);

        return new ResponseEntity<>("The file has been saved", HttpStatus.CREATED);
    }

    @GetMapping("/attachments")
    public ResponseEntity<Collection<AttachmentResponseDTO>> showAttachments(){

        return new ResponseEntity<>(attachmentService.getAllAttachments(), HttpStatus.OK);
    }

}
