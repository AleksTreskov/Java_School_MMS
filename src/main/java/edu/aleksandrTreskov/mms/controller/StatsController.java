package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.service.ClientService;
import edu.aleksandrTreskov.mms.service.ItemService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/stats")
public class StatsController {
    private final ClientService clientService;
    private final PurchaseService purchaseService;
    private final ItemService itemService;

    @GetMapping
    public String findTop10ByPurchases(Model model) {
        model.addAttribute("items", itemService.findTop10SoldItems());
        model.addAttribute("clients", clientService.findTop10ByPurchases());

        return "stats";
    }

//    @GetMapping
//    public String findCountOrders(Model model) {
//        purchaseService.findCountOrders();
//    }
//    @GetMapping("/{id}")
//    public String getClient(@PathVariable long id, Model model) {
//        ClientDTO client = clientService.findById(id);
//        model.addAttribute("client", client);
//        return "clients";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteClient(@PathVariable long id) {
//        clientService.deleteClient(id);
//        return "clients";
//    }

}
