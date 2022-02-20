package edu.AleksandrTreskov.mms.controller;

import edu.AleksandrTreskov.mms.entity.Client;
import edu.AleksandrTreskov.mms.entity.Role;
import edu.AleksandrTreskov.mms.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final ClientRepository clientRepository;
    PasswordEncoder passwordEncoder;

    public AuthController(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (clientRepository.findByEmail(client.getEmail()).isPresent())
            throw new IllegalArgumentException("User with that email already exists");
        client.setRole(new Role(2, "USER"));
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/signup")
    public String newUser(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "signup";
    }

}
