package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.exception.OutOfStockException;
import edu.aleksandrTreskov.mms.mapstruct.dto.AddItemToCart;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.CartItem;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.repository.ItemRepository;
import edu.aleksandrTreskov.mms.repository.PurchaseRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class CartService {
    private final ItemRepository itemRepository;
    private final PurchaseRepository purchaseRepository;

    public void addItemToCart(AddItemToCart addItemToCart, Cart cart) {

        Item item = itemRepository.findById(addItemToCart.getId());
        if ((item.getQuantity() - addItemToCart.getQuantity()) < 0) {
            throw new OutOfStockException(String.format("Sorry,there are only %d pcs of %s left.", item.getQuantity(), item.getName()));
        }
        if (itemExistInCartCheck(cart.getCartItems(), addItemToCart.getId()) == -1) {
            CartItem cartItem = new CartItem(
                    ItemMapper.INSTANCE.toDTO(item),
                    addItemToCart.getQuantity());
            cart.getCartItems().add(cartItem);
        } else {
            cart.getCartItems()
                    .get(itemExistInCartCheck(cart.getCartItems(), addItemToCart.getId()))
                    .addQuantity(addItemToCart.getQuantity());
        }
    }

    public int itemExistInCartCheck(List<CartItem> cartItems, long id) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getItemDTO().getId() == id)
                return i;
        }
        return -1;
    }

    public int countTotalPriceInCart(Cart cart) {

        int totalPrice = 0;
        for (CartItem cartItem : cart.getCartItems()
        ) {
            totalPrice += cartItem.getItemDTO().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    public int countItemsInCart(Cart cart) {
        int totalQuantity = 0;
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems
        ) {
            totalQuantity += cartItem.getQuantity();
        }
        return totalQuantity;
    }

    public void fillCartByRepeatedPurchaseItems(Cart cart, long id) {

        Optional<Purchase> optPurchase = purchaseRepository.findById(id);
        if (optPurchase.isPresent()) {
            Purchase purchase = optPurchase.get();
            for (Item item : purchase.getItems()
            ) {
                if (itemExistInCartCheck(cart.getCartItems(), item.getId()) == -1) {
                    cart.getCartItems().add(new CartItem(ItemMapper.INSTANCE.toDTO(item), 1));
                } else {
                    cart.getCartItems().get(itemExistInCartCheck(cart.getCartItems(), item.getId())).addQuantity(1);
                }
            }

        }
    }
}
