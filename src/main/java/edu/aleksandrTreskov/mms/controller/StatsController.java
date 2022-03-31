package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.common.Months;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ItemService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/stats")
public class StatsController {
    private final ProfileService profileService;
    private final ItemService itemService;
    private final CartService cartService;
    private final PurchaseService purchaseService;

    @GetMapping
    public String findStatisticsForApp(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("items", itemService.findTop10SoldItems());
        model.addAttribute("topClients", profileService.findTop10ByPurchases());
        return "stats";
    }

    @GetMapping("/getStatsWithChosenMonth")
    public String changeMonthForPurchases(@RequestParam("month") Months month, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("items", itemService.findTop10SoldItems());
        model.addAttribute("topClients", profileService.findTop10ByPurchases());
        model.addAttribute("currentMonthPurchases", purchaseService.findPurchasesForThisMonth(month.getValue()));
        double valueForChosenMonth = purchaseService.findPurchasesForThisMonth(month.getValue()).stream().mapToDouble(Purchase::getTotalPrice).sum();
        model.addAttribute("valueForChosenMonth", valueForChosenMonth);
        model.addAttribute("chosenMonth", month);
        return "stats";
    }


}
