package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ClientService;
import edu.aleksandrTreskov.mms.service.ItemService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/stats")
public class StatsController {
    private final ClientService clientService;
    private final ItemService itemService;
    private final CartService cartService;

    @GetMapping
    public String findStatisticsForApp(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cartService.countItemsinCart(cart));
        model.addAttribute("items", itemService.findTop10SoldItems());
        model.addAttribute("clients", clientService.findTop10ByPurchases());

        return "stats";
    }


}
