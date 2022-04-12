package edu.aleksandrTreskov.mms.junit;

import edu.aleksandrTreskov.mms.dto.AddItemToCart;
import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.repository.ItemRepository;
import edu.aleksandrTreskov.mms.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemRepository itemRepository;

    public CartServiceTest() {

    }

    @Test
    public void deleteAddToBucket() {
        Cart cart = new Cart();
        Item item = itemRepository.findById(7);
        AddItemToCart addItemToCart=new AddItemToCart(item.getId(), 3);
        cartService.addItemToCart(addItemToCart, cart);
        assertTrue(cart.getCartItems().size() > 0);
        cartService.removeItemFromCart(0, cart);
        assertEquals(cart.getCartItems().size(), 0);

    }
}

