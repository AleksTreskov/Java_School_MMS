package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }

    @PostMapping
    public String saveNewUser(@ModelAttribute("client") Client client) {
        clientService.saveClient(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/signup")
    public String newUser(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "signup";
    }

}
