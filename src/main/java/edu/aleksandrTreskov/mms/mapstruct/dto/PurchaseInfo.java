package edu.aleksandrTreskov.mms.mapstruct.dto;

import edu.aleksandrTreskov.mms.common.PaymentMethod;
import edu.aleksandrTreskov.mms.common.ShipmentMethod;
import edu.aleksandrTreskov.mms.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseInfo {
    private Long addressId;
    private PaymentMethod paymentMethod;
    private ShipmentMethod deliveryMethod;

}
