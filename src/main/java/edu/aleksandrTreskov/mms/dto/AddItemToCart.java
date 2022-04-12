package edu.aleksandrTreskov.mms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemToCart {
    private long productId;
    private int quantity;


}
