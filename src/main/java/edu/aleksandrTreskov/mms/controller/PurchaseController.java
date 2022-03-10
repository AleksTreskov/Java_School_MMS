package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.PurchaseInfo;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ClientService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping()
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final ClientService clientService;
    private final CartService cartService;

    @GetMapping("/purchases/all")
    public String getAllPurchases(Model model) {
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        return "adminPurchases";
    }

    @GetMapping("/purchases")
    public String getAllPurchases(Model model, Principal principal, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount",cartService.countItemsinCart(cart));
        model.addAttribute("purchases", purchaseService.getAllPurchasesForClient(principal.getName()));
        model.addAttribute("cart", cart);
        return "purchases";
    }

    @GetMapping("/checkout")
    public String addPurchase(Model model, HttpSession session, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        int totalPrice = cartService.countTotalPriceInCart(cart);
        model.addAttribute("total", totalPrice);
        model.addAttribute("addresses", clientService.findByEmail(principal.getName()).getAddresses());
        model.addAttribute("cartCount", cart.getCartItems().size());
        model.addAttribute("cart", cart);
        return "checkout";
    }


    @PostMapping("/checkout/confirm")
    public String savePurchase(@RequestBody PurchaseInfo purchaseInfo, HttpSession session, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        Purchase purchase = new Purchase();
        purchase.setClient(clientService.findByEmail(principal.getName()));
        purchaseService.savePurchase(cart, purchaseInfo, purchase);
        session.setAttribute("cart",new Cart());
        return "redirect:/profile";
    }
}
