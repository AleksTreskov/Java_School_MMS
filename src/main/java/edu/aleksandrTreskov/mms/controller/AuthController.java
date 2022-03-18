package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ProfileService profileService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


    @PostMapping
    public String saveNewUser(@ModelAttribute("client") Client client, Model model) {

        profileService.saveClient(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/signup")
    public String newUser(Model model) {
        model.addAttribute("client", new Client());
        return "signup";
    }

}
