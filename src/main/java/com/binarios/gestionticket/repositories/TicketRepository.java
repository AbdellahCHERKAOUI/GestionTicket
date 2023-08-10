package com.binarios.gestionticket.repositories;

import com.binarios.gestionticket.entities.Attachment;
import com.binarios.gestionticket.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
