package com.binarios.gestionticket.webcontroller;


import org.springframework.stereotype.Controller;

import com.binarios.gestionticket.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TicketWebController {
    private TicketRepository homeRepository;

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }
       @GetMapping(path = "/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "admin";
    }
    @GetMapping(path = "/tech")
    //@PreAuthorize("hasRole('TECH')")
    public String techPage() {
        return "tech";
    }

    //@PreAuthorize("hasRole('CLIENT')")
    @GetMapping(path = "/client")
    public String clientPage() {
        return "client";
    }

    @GetMapping(path = "/group-details.html")
    public String groupDetails() {
        return "group-details";
    }

    @GetMapping(path = "/ticket")
    public String ticket() {
        return "ticket-hold";
    }
    @GetMapping(path = "/ticket-details")
    public String ticketDetails() {
        return "ticket-details";
    }

    @GetMapping(path = "/assign-ticket-details")
    public String assignTicket() {
        return "Assign-Ticket-Details";
    }

    @GetMapping(path = "/users")
    public String showUsers() {
        return "users";
    }
}