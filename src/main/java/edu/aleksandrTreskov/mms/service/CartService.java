package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.mapstruct.dto.AddItemToCart;
import edu.aleksandrTreskov.mms.mapstruct.dto.Cart;
import edu.aleksandrTreskov.mms.mapstruct.dto.CartItem;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.repository.ItemRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CartService {
    private final ItemRepository itemRepository;

    public void addItemToCart(AddItemToCart addItemToCart, Cart cart) {

        Item item = itemRepository.findById(addItemToCart.getId());

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

    public int countItemsinCart(Cart cart) {
        int totalQuantity = 0;
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems
        ) {
            totalQuantity += cartItem.getQuantity();
        }
        return totalQuantity;
    }
}
