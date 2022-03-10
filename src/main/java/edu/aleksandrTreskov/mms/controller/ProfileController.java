package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.service.AddressService;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ClientService clientService;
    private final CartService cartService;
    private final AddressService addressService;

    @GetMapping
    public String getClientProfile(HttpSession session, Model model, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("addresses", addressService.getAllAddressesByEmail(principal.getName()));
        model.addAttribute("cartCount", cartService.countItemsinCart(cart));
        model.addAttribute("client", clientService.findByEmail(principal.getName()));
        return "profile";
    }



    @PatchMapping()
    public String updateProfile(@ModelAttribute Client client) {

        clientService.saveClient(client);
        return "profile";
    }
}
