package com.binarios.gestionticket.webcontroller;


import org.springframework.stereotype.Controller;

import com.binarios.gestionticket.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TicketWebController {
       @GetMapping(path = "/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "admin";
    }

    @GetMapping(path = "/login")
    //@PreAuthorize("hasRole('ADMIN')")
    public String loginPage() {
        return "login";
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
}