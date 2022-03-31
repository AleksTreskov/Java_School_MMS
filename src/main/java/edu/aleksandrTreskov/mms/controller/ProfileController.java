package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.*;
import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.mapstruct.mapper.AddressMapper;
import edu.aleksandrTreskov.mms.service.AddressService;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final CartService cartService;
    private final AddressService addressService;

    @GetMapping
    public String getClientProfile(HttpSession session, Model model, Principal principal) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("addresses", addressService.findAllAddressesByEmail(principal.getName()));
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("client", profileService.findByEmail(principal.getName()));
        return "profile";
    }

    @ResponseBody
    @PostMapping("/updateMainInfo")
    public ResponseAttribute updateMainInfo(@RequestBody ClientDTO clientDTO, Principal principal) {
        Client client = profileService.findByEmail(principal.getName());
        profileService.updateClient(clientDTO, client);
        return ResponseAttribute.builder().error(false).build();
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public ResponseAttribute changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, Principal principal, Model model, HttpSession session) {

        profileService.updatePassword(changePasswordDTO, profileService.findByEmail(principal.getName()));
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("addresses", addressService.findAllAddressesByEmail(principal.getName()));
        model.addAttribute("cartCount", cartService.countItemsInCart(cart));
        model.addAttribute("client", profileService.findByEmail(principal.getName()));
        return ResponseAttribute.builder().error(false).build();
    }


    @PostMapping("/deleteAddressById")
    @ResponseBody
    public ResponseAttribute deleteAddressById(@RequestBody Integer deleteId) {
        addressService.deleteAddress(deleteId);
        return ResponseAttribute.builder().error(false).build();

    }

    @ResponseBody
    @PostMapping("/saveEditAddress")
    public AddressDTO saveEditAddress(@Valid @RequestBody AddressDTO addressDTO, Principal principal) {
        addressService.saveAddress(AddressMapper.INSTANCE.toAddress(addressDTO), profileService.findByEmail(principal.getName()));
        return AddressMapper.INSTANCE.toDTO(addressService.findById(addressDTO.getId()));
    }

    @ResponseBody
    @PostMapping("/saveNewAddress")
    public AddressDTO saveNewAddress(@Valid @RequestBody AddressDTO addressDTO, Principal principal) {

        Address address = addressService.saveAddress(AddressMapper.INSTANCE.toAddress(addressDTO), profileService.findByEmail(principal.getName()));
        return AddressMapper.INSTANCE.toDTO(address);
    }

    @ResponseBody
    @PostMapping("/editAddress")
    public AddressDTO editAddress(@RequestBody Long id) {
        return AddressMapper.INSTANCE.toDTO(addressService.findById(id));
    }
}
