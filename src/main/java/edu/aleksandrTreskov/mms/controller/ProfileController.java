package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    ClientService clientService;

    public ProfileController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String getInfoAboutClient(Model model, Principal principal) {
        model.addAttribute("client", clientService.findByEmail(principal.getName()));
        return "profile";

    }
}
