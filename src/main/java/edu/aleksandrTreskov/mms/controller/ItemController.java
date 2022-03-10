package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.ItemDTO;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CartService cartService;

    @GetMapping("/catalog/{category}")
    public String filterByCategory(@PathVariable String category, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("items", itemService.findAllByCategory(category));
        model.addAttribute("categories", itemService.findCategories());
        model.addAttribute("cartCount", cartService.countItemsinCart(cart));
        return "items";
    }

    @PostMapping("/newItem/add")
    public String saveItem(@ModelAttribute Item item) {
        itemService.saveItem(item);
        return "redirect:/catalog";
    }

    @GetMapping("/newItem")
    public String saveItem(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount",cartService.countItemsinCart(cart));
        model.addAttribute("item", new ItemDTO());
        model.addAttribute("categories", itemService.findCategories());
        return "newItem";
    }

    @GetMapping({"/", "/catalog"})
    public String getAllItems(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cartCount", cartService.countItemsinCart(cart));
        model.addAttribute("categories", itemService.findCategories());
        model.addAttribute("items", itemService.getAllItems());
        return "items";
    }

    @GetMapping("/top")
    public String getTop10(Model model) {
        model.addAttribute("items", itemService.getTop10());
        return "items";
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable long id, Model model) {
        model.addAttribute("parameters", itemService.getItem(id).getParameters());
        model.addAttribute("item", itemService.getItem(id));
        return "item";
    }
}
