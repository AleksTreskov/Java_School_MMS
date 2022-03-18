package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.mapstruct.dto.AddItemToCart;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.ResponseAttribute;
import edu.aleksandrTreskov.mms.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping()
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("cart", cart.getCartItems());
        model.addAttribute("total", cartService.countTotalPriceInCart(cart));

        return "cart";

    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeItemFromCart(@PathVariable int index, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");

        cart.getCartItems().remove(index);
        return "redirect:/cart";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public ResponseAttribute addItemToCart(@RequestBody AddItemToCart addItemToCart, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        cartService.addItemToCart(addItemToCart, cart);
        return ResponseAttribute.builder().error(false).build();
    }
}
