package edu.aleksandrTreskov.mms.dto;

import edu.aleksandrTreskov.mms.common.PaymentMethod;
import edu.aleksandrTreskov.mms.common.ShipmentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseInfo {
    private Long addressId;
    private PaymentMethod paymentMethod;
    private ShipmentMethod deliveryMethod;
}
