package edu.aleksandrTreskov.mms.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }
}
