package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.common.OrderStatus;
import edu.aleksandrTreskov.mms.common.PaymentStatus;
import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.CartItem;
import edu.aleksandrTreskov.mms.mapstruct.dto.PurchaseDTO;
import edu.aleksandrTreskov.mms.mapstruct.dto.PurchaseInfo;
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
        purchase.setAddress(addressRepository.findById(purchaseInfo.getAddressId()));

        List<Item> items = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            for (int i = 0; i < cartItem.getQuantity(); i++) {
                items.add(ItemMapper.INSTANCE.toItem(cartItem.getItemDTO()));
            }
            cartItem.getItemDTO().setQuantity(cartItem.getItemDTO().getQuantity() - cartItem.getQuantity());
            cartItem.getItemDTO().setSold(cartItem.getItemDTO().getSold() + cartItem.getQuantity());
            itemRepository.save(ItemMapper.INSTANCE.toItem(cartItem.getItemDTO()));
        }
        purchase.setItems(items);
        purchase.setPaymentMethod(purchaseInfo.getPaymentMethod());
        purchase.setShipmentMethod(purchaseInfo.getDeliveryMethod());
        purchase.setOrderStatus(OrderStatus.WAITINGFORSHIPMENT);
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
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAllPurchases();
    }

    public Optional<Purchase> findById(Integer orderId) {
        return purchaseRepository.findById(orderId);
    }

    public int itemExistInCartCheck(List<CartItem> cartItems, long id) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getItemDTO().getId() == id)
                return i;
        }
        return -1;
    }


}

