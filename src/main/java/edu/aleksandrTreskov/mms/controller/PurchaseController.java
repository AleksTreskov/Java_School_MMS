package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.PurchaseInfo;
import edu.aleksandrTreskov.mms.dto.PurchaseStatusInfo;
import edu.aleksandrTreskov.mms.dto.ResponseAttribute;
import edu.aleksandrTreskov.mms.service.AddressService;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping()
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final ProfileService profileService;
    private final CartService cartService;
    private final AddressService addressService;

    @GetMapping("/purchases/all")
    public String findAllPurchases(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("purchases", purchaseService.getAllPurchases());
        return "adminPurchases";
    }

    @PostMapping("/purchases/all/changePurchaseStatus")
    public String changePurchaseStatus(@RequestBody PurchaseStatusInfo purchaseStatusInfo) {
        purchaseService.changePurchaseStatus(purchaseStatusInfo);
        return "purchases";
    }

    @GetMapping("/purchases")
    public String findAllClientPurchases(Model model, Principal principal, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("purchases", purchaseService.getAllPurchasesForClient(principal.getName()));
        model.addAttribute("cart", cart);
        return "purchases";
    }

    @GetMapping("/checkout")
    public String addPurchase(Model model, HttpSession session, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        int totalPrice = cartService.countTotalPriceInCart(cart);
        model.addAttribute("total", totalPrice);
        model.addAttribute("addresses", addressService.findAllAddressesByEmail(principal.getName()));
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("cart", cart);
        return "checkout";
    }

    @ResponseBody
    @PostMapping("/checkout/confirm")
    public ResponseAttribute savePurchase(@RequestBody PurchaseInfo purchaseInfo, HttpSession session, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        Purchase purchase = new Purchase();
        purchase.setClient(profileService.findByEmail(principal.getName()));
        purchaseService.savePurchase(cart, purchaseInfo, purchase);
        session.setAttribute("cart", new Cart());
        return ResponseAttribute.builder().error(false).build();
    }

    @GetMapping("/purchases/repeat/{id}")
    public String repeatPurchase(@PathVariable long id, HttpSession session) {
        session.setAttribute("cart", new Cart());
        Cart cart = (Cart) session.getAttribute("cart");

        cartService.fillCartByRepeatedPurchaseItems(cart, id);

        return "redirect:/cart";
    }
}
