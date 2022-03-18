package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.ItemDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CartService cartService;


    @GetMapping("/{category}/{pageNo}")
    public String filterByCategory(@PathVariable("category") String category, @PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                   @RequestParam("sortDir") String sortDir, Model model, HttpSession session) {
        int pageSize = 7;
        loadNavAttributesForItemsModel(model, session);
        model.addAttribute("chosenCategory", category);
        Page<Item> page = itemService.findPaginatedCategory(pageNo, pageSize, sortField, sortDir,category);
        List<Item> items = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("items", items);
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
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("item", new ItemDTO());
        model.addAttribute("categories", itemService.findCategories());

        return "newItem";
    }

    @GetMapping({"/", "/catalog"})
    public String homePage(Model model, HttpSession session) {

        return findPaginated(1, "id", "asc", model, session);
    }

//    @GetMapping("/top")
//    public String findTop10(Model model) {
//        model.addAttribute("items", itemService.findTop10());
//        return "items";
//    }

    @GetMapping("/item/{id}")
    public String findItemById(@PathVariable long id, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));

        return "item";
    }

    @ResponseBody
    @PostMapping("/editItem")
    public ItemDTO findItemForEdit(@RequestBody Long id) {
        ItemDTO item = itemService.findById(id);
        return item;
    }

    @ResponseBody
    @PostMapping("/saveEditItem")
    public ItemDTO saveEditedItem(@RequestBody ItemDTO itemDTO) {
        itemService.saveItem(ItemMapper.INSTANCE.toItem(itemDTO));
        return itemService.findById(itemDTO.getId());
    }

    @DeleteMapping("/item/{id}")
    public String deleteById(@PathVariable long id, Model model, HttpSession session) {
        itemService.deleteById(id);
        loadNavAttributesForItemsModel(model, session);
        model.addAttribute("items", itemService.findAllItems());
        return "items";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable("pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model, HttpSession session) {

        int pageSize = 7;
        loadNavAttributesForItemsModel(model, session);
        Page<Item> page = itemService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Item> items = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("items", items);
        return "items";
    }

    public void loadNavAttributesForItemsModel(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("categories", itemService.findCategories());

    }
}
