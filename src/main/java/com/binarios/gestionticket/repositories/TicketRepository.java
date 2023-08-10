package com.binarios.gestionticket.repositories;

import com.binarios.gestionticket.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query(value = "SELECT * FROM ticket t WHERE t.client_id = :personId OR t.assigned_tech_id = :personId OR t.admin_id = :personId", nativeQuery = true)
    List<Ticket> findTicketsByPersonId(Long personId);
}
