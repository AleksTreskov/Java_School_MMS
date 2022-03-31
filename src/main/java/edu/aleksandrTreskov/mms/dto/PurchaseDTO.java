package edu.aleksandrTreskov.mms.dto;

import edu.aleksandrTreskov.mms.entity.Purchase;
import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Purchase purchase;
    private List<CartItem> cartItems;
}
