package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ProfileService profileService;
    private final CartService cartService;

    @GetMapping("/login")
    public String getLoginPage(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        return "login";
    }


    @PostMapping
    public String saveNewUser(@ModelAttribute("client") Client client) {
        profileService.saveClient(client);
        return "redirect:/auth/login";
    }

    @GetMapping("/signup")
    public String newUser(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("client", new Client());
        return "signup";
    }

}
