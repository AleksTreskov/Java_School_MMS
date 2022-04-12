package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.dto.AddItemToCart;
import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.CartItem;
import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.exception.OutOfStockException;
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
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    /**
     *Adds item to cart, if item's quantity is not enough - throws exception
     *
     * @param addItemToCart AddItemToCart - DTO for response body
     * @param cart Cart
     */
    public void addItemToCart(AddItemToCart addItemToCart, Cart cart) {

        Item item = itemRepository.findById(addItemToCart.getProductId());
        if ((item.getQuantity() - addItemToCart.getQuantity()) < 0) {
            throw new OutOfStockException(String.format("Sorry,there are only %d pcs of %s left.", item.getQuantity(), item.getName()));
        }
        if (itemExistInCartCheck(cart.getCartItems(), addItemToCart.getProductId()) == -1) {
            CartItem cartItem = new CartItem(
                    itemMapper.toDTO(item),
                    addItemToCart.getQuantity());
            cart.getCartItems().add(cartItem);
        } else {
            cart.getCartItems()
                    .get(itemExistInCartCheck(cart.getCartItems(), addItemToCart.getProductId()))
                    .addQuantity(addItemToCart.getQuantity());
        }
    }

    /**
     * Checks, if item exists in cart, or not, using List's index, if exists - return index
     *
     * @param cartItems List<CartItem></>
     * @param id index
     * @return int
     */
    private int itemExistInCartCheck(List<CartItem> cartItems, long id) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getItemDTO().getId() == id)
                return i;
        }
        return -1;
    }

    /**
     * Counts total price in cart, by multiplying item's price on quantity
     *
     * @param cart Cart
     * @return total price
     */
    public int countTotalPriceInCart(Cart cart) {

        int totalPrice = 0;
        for (CartItem cartItem : cart.getCartItems()
        ) {
            totalPrice += cartItem.getItemDTO().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    /**
     * Counts current items in cart for adding it to view.
     *
     * @param cart Cart
     * @return cart size
     */
    public int countItemsInCart(Cart cart) {
        int totalQuantity = 0;
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems
        ) {
            totalQuantity += cartItem.getQuantity();
        }
        return totalQuantity;
    }

    /**
     * This method is for repeat purchase button. It takes all items from chosen purchase and transfers to cart
     *
     * @param cart Cart
     * @param id purchase ID
     */
    public void fillCartByRepeatedPurchaseItems(Cart cart, long id) {

        Optional<Purchase> optPurchase = purchaseRepository.findById(id);
        if (optPurchase.isPresent()) {
            Purchase purchase = optPurchase.get();
            for (Item item : purchase.getItems()
            ) {
                if (itemExistInCartCheck(cart.getCartItems(), item.getId()) == -1) {
                    cart.getCartItems().add(new CartItem(itemMapper.toDTO(item), 1));
                } else {
                    cart.getCartItems().get(itemExistInCartCheck(cart.getCartItems(), item.getId())).addQuantity(1);
                }
            }

        }
    }


    public void removeItemFromCart(int index, Cart cart) {
        cart.getCartItems().remove(index);
    }
}
