package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.ResponseAttribute;
import edu.aleksandrTreskov.mms.service.EmailService;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.exception.WrongActivationCodeException;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ProfileService profileService;
    private final CartService cartService;
    private final EmailService emailService;
    private int activationCode;

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

    @PostMapping("/sendActivationCode")
    @ResponseBody
    public ResponseAttribute sendActivationCode(@RequestBody String email) {
        int a = 999;
        int b = 9999;
        activationCode = a + (int) (Math.random() * ((b - a) + 1));
        System.out.println(activationCode);
        System.out.println(email);
        email = email.substring(1,email.length()-1);
        System.out.println(email);
        emailService.sendSimpleEmail(email, "Activation code for Eshop.com", String.format("Welcome to Eshop! To become the part of our community, please, enter the activation code %d", activationCode));
        return ResponseAttribute.builder().error(false).build();
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public ResponseAttribute verifyCode(@RequestBody int code) {
        if (activationCode == code)
            return ResponseAttribute.builder().error(false).build();
        else
            throw new WrongActivationCodeException("Code is not correct");
    }

}
