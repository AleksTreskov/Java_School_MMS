package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.ItemDTO;
import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CartService cartService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    @GetMapping("/{category}/{pageNo}")
    public String filterByCategory(@PathVariable("category") String category, @PathVariable("pageNo") int pageNo, @RequestParam("sortField") String sortField,
                                   @RequestParam("sortDir") String sortDir, Model model, HttpSession session) {
        int pageSize = 7;
        loadNavAttributesForItemsModel(model, session);
        model.addAttribute("chosenCategory", category);
        Page<Item> page = itemService.findPaginatedCategory(pageNo, pageSize, sortField, sortDir, category);
        return getPageContent(pageNo, sortField, sortDir, model, page);
    }


    @PostMapping("/newItem/add")
    public String saveItem(@ModelAttribute Item item, @RequestParam("productImage") MultipartFile file) {
        String img = file.getOriginalFilename();

        item.setImgName(img);
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

    @GetMapping("/catalog/search")
    public String searchByWord(@RequestParam("searchText") String searchText, Model model, HttpSession session) {
        loadNavAttributesForItemsModel(model, session);
        model.addAttribute("items", itemService.searchByText(searchText));
        return "items";

    }

    @GetMapping({"/", "/catalog"})
    public String homePage(@RequestParam(value = "category", required = false) String category, Model model, HttpSession session) {

        return findPaginated(1, category, "id", "asc", model, session);
    }

//    @GetMapping("/top")
//    public String findTop10(Model model) {
//        model.addAttribute("items", itemService.findTop10());
//        return "items";
//    }

    @GetMapping("/item/{id}")
    public String findItemById(@PathVariable long id, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
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

        itemService.saveItem(itemMapper.toItem(itemDTO));
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
                                @RequestParam(value = "category", required = false) String category,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir, Model model, HttpSession session) {

        int pageSize = 7;
        Page<Item> page;
        loadNavAttributesForItemsModel(model, session);
        if (category != null) {
            page = itemService.findPaginatedCategory(pageNo, pageSize, sortField, sortDir, category);
            model.addAttribute("chosenCategory", category);
        } else
            page = itemService.findPaginated(pageNo, pageSize, sortField, sortDir);
        return getPageContent(pageNo, sortField, sortDir, model, page);
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

    private String getPageContent(int pageNo, String sortField, String sortDir, Model model, Page<Item> page) {
        List<Item> items = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("items", items);
        return "items";
    }
}
