package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {
   private final ClientService clientService;


    @GetMapping
    public String getClientProfile(Model model, Principal principal) {
        model.addAttribute("client", clientService.findByEmail(principal.getName()));
        return "profile";
    }
    @GetMapping("/settings")
    public String getProfileSettings(Model model, Principal principal) {
        model.addAttribute("client", clientService.findByEmail(principal.getName()));
        return "profileSettings";
    }

    @PatchMapping()
    public String updateProfile(@ModelAttribute Client client) {

        clientService.saveClient(client);
        return "profile";
    }
}
