package com.binarios.gestionticket.service;

import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.entities.Ticket;
import com.binarios.gestionticket.repositories.AttachmentRepository;
import com.binarios.gestionticket.repositories.TicketRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class AttachmentService {

    private static final String UPLOAD_DIR = "C://Users//Electro-Market.ma//Desktop//saving";


    private final AttachmentRepository attachmentRepository;
    private final TicketRepository ticketRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, TicketRepository ticketRepository) {
        this.attachmentRepository = attachmentRepository;
        this.ticketRepository = ticketRepository;
    }

    public Attachment saveFile(MultipartFile file){
        Attachment attachmentToSend = new Attachment();
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileType = file.getContentType();
            String folderName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            try {
                // Create the folder that we will store the file in
                String uploadFolderPath = UPLOAD_DIR + "/" + folderName;
                File folder = new File(uploadFolderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                try (InputStream inputStream = file.getInputStream()) {
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                    Path filePath = Paths.get(uploadFolderPath).resolve(uniqueFileName);

                    // Create the Attachment entity and set its properties
                    Attachment attachment = new Attachment();
                    attachment.setFileName(fileName);
                    attachment.setFileType(fileType);
                    attachment.setFilePath(filePath.toString());

                    attachmentToSend = attachment;

                    attachmentRepository.save(attachment);

                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return attachmentToSend ;
    }



    public Attachment addFileToTicket(MultipartFile file, Long ticketId) throws Exception{
        Attachment attachmentToSend = new Attachment();
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null){
            throw new Exception("There is no ticket with this id : "+ticketId);
        }
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileType = file.getContentType();
            String folderName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            try {
                // Create the folder that we will store the file in
                String uploadFolderPath = UPLOAD_DIR + "/" + folderName;
                File folder = new File(uploadFolderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                try (InputStream inputStream = file.getInputStream()) {
                    String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                    Path filePath = Paths.get(uploadFolderPath).resolve(uniqueFileName);

                    // Create the Attachment entity and set its properties
                    Attachment attachment = new Attachment();
                    attachment.setFileName(fileName);
                    attachment.setFileType(fileType);
                    attachment.setFilePath(filePath.toString());
                    //Add the attachment to the corresponding ticket
                    ticket.getAttachments().add(attachment);

                    attachmentToSend = attachment;

                    attachmentRepository.save(attachment);

                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return attachmentToSend;
    }

    public void deleteAttachmentById(Long attachmentId) throws Exception{
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);
        if(attachment == null){
            throw new Exception("There is no attachment with this id "+attachmentId);
        }
        attachmentRepository.deleteById(attachmentId);
    }
}
