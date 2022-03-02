package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final CartService cartService;


    @GetMapping
    public String getAllPurchases(Model model, Principal principal) {
        model.addAttribute("purchases", purchaseService.getAllPurchases(principal.getName()));
        return "purchases";
    }

    @GetMapping("/new")
    public String addPurchase(Model model) {
        model.addAttribute("purchase", new Purchase());
        model.addAttribute("cart", cartService.productsInCart());
        return "new";
    }

    @PostMapping
    public String savePurchase(@ModelAttribute Purchase purchase, Principal principal, @ModelAttribute Address address) {
        purchaseService.save(purchase, principal,address);
        return "purchases";
    }
}
