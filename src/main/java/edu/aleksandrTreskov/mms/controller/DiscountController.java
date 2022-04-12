package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.ResponseAttribute;
import edu.aleksandrTreskov.mms.entity.DiscountCode;
import edu.aleksandrTreskov.mms.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/promo")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping
    public String getPromoPage(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cart.getCartItems().size());
        model.addAttribute("discounts", discountService.findAll());
        return "promo";
    }

    @ResponseBody
    @PostMapping("/useDiscountCode")
    public ResponseAttribute useCode(@RequestBody String code, HttpSession session, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        int percent = discountService.findCode(cart, code, principal.getName());
        return ResponseAttribute.builder().error(false).discountPercent(percent).build();
    }

    @ResponseBody
    @PostMapping("/saveNewDiscount")
    public ResponseAttribute createNewDiscount(@RequestBody DiscountCode discountCode) {
        discountService.save(discountCode);
        return ResponseAttribute.builder().error(false).build();
    }

    @GetMapping("/{id}")
    public String deleteDiscountCode(HttpSession session, Model model, @PathVariable long id) {
        discountService.delete(id);
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cart.getCartItems().size());
        model.addAttribute("discounts", discountService.findAll());
        return "promo";
    }
}
