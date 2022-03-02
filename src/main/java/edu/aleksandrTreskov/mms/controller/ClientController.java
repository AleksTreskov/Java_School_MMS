package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.mapstruct.dto.ClientDTO;
import edu.aleksandrTreskov.mms.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/clients")
public class ClientController {
    private final ClientService clientService;


    @GetMapping
    public String findTop10ByPurchases(Model model) {
        model.addAttribute("clients", clientService.findTop10ByPurchases());
        return "clients";
    }

    @GetMapping("/{id}")
    public String getClient(@PathVariable long id, Model model) {
        ClientDTO client = clientService.findById(id);
        model.addAttribute("client", client);
        return "clients";
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return "clients";
    }

}
