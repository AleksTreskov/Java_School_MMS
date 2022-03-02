package edu.aleksandrTreskov.mms.mapstruct.dto;

import edu.aleksandrTreskov.mms.common.PaymentMethod;
import edu.aleksandrTreskov.mms.common.PaymentStatus;
import edu.aleksandrTreskov.mms.common.ShipmentMethod;
import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.entity.Client;
import edu.aleksandrTreskov.mms.common.OrderStatus;
import edu.aleksandrTreskov.mms.entity.Item;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private long id;
    private Client client;
    private Address address;
    private int totalPrice;
    private PaymentMethod paymentMethod;
    private ShipmentMethod shipmentMethod;
    private List<Item> items;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
}
