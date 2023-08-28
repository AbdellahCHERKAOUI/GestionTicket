package com.binarios.gestionticket.repositories;

import com.binarios.gestionticket.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
