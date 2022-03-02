package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ItemController {
    private final ItemService itemService;


    @PostMapping("/add")
    public String saveItem(@ModelAttribute Item item) {
        itemService.saveItem(item);
        return "items";
    }

    @GetMapping("/newItem")
    public String saveItem(Model model) {
        model.addAttribute("item", new Item());
        return "newItem";
    }

    @GetMapping("/catalog")
    public String getAllItems(Model model) {
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
        model.addAttribute("parameters",itemService.getItem(id).getParameters());
        model.addAttribute("item", itemService.getItem(id));
        return "item";
    }

}
