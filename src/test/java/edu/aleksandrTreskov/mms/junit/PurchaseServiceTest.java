package edu.aleksandrTreskov.mms.junit;

import edu.aleksandrTreskov.mms.common.PaymentMethod;
import edu.aleksandrTreskov.mms.common.ShipmentMethod;
import edu.aleksandrTreskov.mms.dto.AddItemToCart;
import edu.aleksandrTreskov.mms.dto.Cart;
import edu.aleksandrTreskov.mms.dto.PurchaseInfo;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.mapstruct.mapper.AddressMapper;
import edu.aleksandrTreskov.mms.repository.ClientRepository;
import edu.aleksandrTreskov.mms.service.AddressService;
import edu.aleksandrTreskov.mms.service.CartService;
import edu.aleksandrTreskov.mms.service.ProfileService;
import edu.aleksandrTreskov.mms.service.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PurchaseServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressService addressService;

    public PurchaseServiceTest() {

    }

    @Test
    public void makeNewPurchaseTest() {
        Purchase purchaseBefore = new Purchase();
        Optional<Client> client = clientRepository.findByEmail("Abc@gmail.com");
        purchaseBefore.setClient(client.get());
        purchaseBefore.setAddress(addressService.findById(2));
        Cart cart = new Cart();
        AddItemToCart addItemToCart = new AddItemToCart(7, 1);
        cartService.addItemToCart(addItemToCart, cart);
        purchaseService.savePurchase(cart, new PurchaseInfo(addressService.findAllAddressesByEmail("Abc@gmail.com").get(0).getId(),"WELCOME10",
                PaymentMethod.CASH, ShipmentMethod.DELIVERY), purchaseBefore);
        Purchase purchaseAfter = purchaseService.getPurchaseById(purchaseBefore.getId());
        System.out.println(purchaseAfter);
        assertEquals(purchaseBefore.getItems(), purchaseAfter.getItems());

    }
}
