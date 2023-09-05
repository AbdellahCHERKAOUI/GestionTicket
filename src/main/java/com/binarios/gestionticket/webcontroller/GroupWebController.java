package com.binarios.gestionticket.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class GroupWebController {

    @GetMapping("/group")
    public String dash(){
        return "group";
    }
}
