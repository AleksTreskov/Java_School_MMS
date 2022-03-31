package edu.aleksandrTreskov.mms.dto;

import lombok.Data;

@Data
public class CartItem {
    private ItemDTO itemDTO;
    private int quantity;

    public CartItem(ItemDTO itemDTO,int quantity){
        this.itemDTO=itemDTO;
        this.quantity=quantity;
    }
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
