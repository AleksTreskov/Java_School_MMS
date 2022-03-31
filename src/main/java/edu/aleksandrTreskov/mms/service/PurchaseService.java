package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.common.PaymentStatus;
import edu.aleksandrTreskov.mms.common.PurchaseStatus;
import edu.aleksandrTreskov.mms.dto.*;
import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.exception.OutOfStockException;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.repository.AddressRepository;
import edu.aleksandrTreskov.mms.repository.ItemRepository;
import edu.aleksandrTreskov.mms.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;
    private final MessageService messageService;

    public List<PurchaseDTO> getAllPurchasesForClient(String email) {
        List<Purchase> purchases = purchaseRepository.getAllPurchasesByClientEmail(email);
        List<PurchaseDTO> purchaseDTOS = new ArrayList<>();
        for (Purchase purchase : purchases
        ) {
            List<CartItem> cartItems = new ArrayList<>();
            for (Item item : purchase.getItems()) {
                if (itemExistInCartCheck(cartItems, item.getId()) == -1) {
                    cartItems.add(new CartItem(ItemMapper.INSTANCE.toDTO(item), 1));
                } else {
                    cartItems.get(itemExistInCartCheck(cartItems, item.getId())).addQuantity(1);
                }
            }
            purchaseDTOS.add(new PurchaseDTO(purchase, cartItems));
        }

        return purchaseDTOS;
    }

    public void savePurchase(Cart cart, PurchaseInfo purchaseInfo, Purchase purchase) {
        Optional<Address> address = addressRepository.findById(purchaseInfo.getAddressId());
        address.ifPresent(purchase::setAddress);
        List<Item> items = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            for (int i = 0; i < cartItem.getQuantity(); i++) {

                items.add(ItemMapper.INSTANCE.toItem(cartItem.getItemDTO()));
            }
            if (itemRepository.findById(cartItem.getItemDTO().getId()).getQuantity() < cartItem.getQuantity())
                throw new OutOfStockException(String.format("Sorry, not enough quantity of %s for your purchase.", cartItem.getItemDTO().getName()));

            cartItem.getItemDTO().setQuantity(itemRepository.findById(cartItem.getItemDTO().getId()).getQuantity() - cartItem.getQuantity());
            cartItem.getItemDTO().setSold(itemRepository.findById(cartItem.getItemDTO().getId()).getSold() + cartItem.getQuantity());
            itemRepository.save(ItemMapper.INSTANCE.toItem(cartItem.getItemDTO()));
        }
        purchase.setItems(items);
        purchase.setPaymentMethod(purchaseInfo.getPaymentMethod());
        purchase.setShipmentMethod(purchaseInfo.getDeliveryMethod());
        purchase.setPurchaseStatus(PurchaseStatus.WAITINGFORSHIPMENT);
        purchase.setPaymentStatus(PaymentStatus.PAID);
        purchase.setDeleted(false);
        int totalPrice = 0;
        for (CartItem item : cart.getCartItems()
        ) {

            totalPrice += item.getItemDTO().getPrice() * item.getQuantity();
        }
        purchase.setTotalPrice(totalPrice);
        purchase.setDateCreated(LocalDateTime.now());

        purchaseRepository.save(purchase);
        messageService.sendMessage();


    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAllPurchases();
    }

    public Optional<Purchase> findById(Integer orderId) {
        return purchaseRepository.findById(orderId);
    }

    private int itemExistInCartCheck(List<CartItem> cartItems, long id) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getItemDTO().getId() == id)
                return i;
        }
        return -1;
    }


    public void changePurchaseStatus(PurchaseStatusInfo purchaseStatusInfo) {
        Optional<Purchase> optPurchase = purchaseRepository.findById(purchaseStatusInfo.getPurchaseId());
        if (optPurchase.isPresent()) {
            Purchase purchase = optPurchase.get();
            purchase.setPurchaseStatus(purchaseStatusInfo.getPurchaseStatus());
            purchaseRepository.save(purchase);
        }

    }

    public List<Purchase> findPurchasesForThisMonth(int month) {
        return purchaseRepository.findPurchasesInMonth(month);
    }
}

