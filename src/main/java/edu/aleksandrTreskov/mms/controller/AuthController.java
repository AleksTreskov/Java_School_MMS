package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.RecoverPasswordDTO;
import edu.aleksandrTreskov.mms.dto.ResponseAttribute;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.exception.EmailAlreadyExistsException;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.EmailService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final ProfileService profileService;
    private final CartService cartService;
    private final EmailService emailService;

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
    public ResponseAttribute sendActivationCodeOnEmail(@RequestBody String email) {

        if (profileService.checkExistingEmail(email))
            throw new EmailAlreadyExistsException("User with this email already exists.");
        emailService.sendSimpleMessage(email, "Activation code for Eshop.com", String.format("Welcome to Eshop! To become the part of our community, please, enter the activation code %d", emailService.getCode()));

        return ResponseAttribute.builder().error(false).build();
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public ResponseAttribute verifyCode(@RequestBody int code) {
        emailService.codeIsVerified(code);
        return ResponseAttribute.builder().error(false).build();

    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public ResponseAttribute sendRecoveryCodeOnPhone(@RequestBody String loginParameter) {
        if (profileService.checkExistingPhone(loginParameter)) {
            emailService.sendSMS(loginParameter);
            return ResponseAttribute.builder().error(false).build();
        }
        if (profileService.checkExistingEmail(loginParameter)) {
            emailService.sendSimpleMessage(loginParameter, "Password recovery", String.format("Your code for password recovery is %d", emailService.getCode()));
            return ResponseAttribute.builder().error(false).build();

        }
        throw new NoSuchElementException("User with this parameter is not found");
    }

    @PostMapping("/setNewPassword")
    @ResponseBody
    public ResponseAttribute setNewPassword(@RequestBody RecoverPasswordDTO recoverPasswordDTO) {
        profileService.setNewPassword(recoverPasswordDTO);
        return ResponseAttribute.builder().error(false).build();
    }

}
