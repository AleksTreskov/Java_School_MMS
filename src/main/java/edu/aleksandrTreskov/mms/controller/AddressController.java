package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.service.AddressService;
import edu.aleksandrTreskov.mms.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService, ClientService clientService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String getAllAddresses(Model model, Principal principal) {
        model.addAttribute("addresses",addressService.getAllAddressesByEmail(principal.getName()));
        return "addresses";
    }

}
