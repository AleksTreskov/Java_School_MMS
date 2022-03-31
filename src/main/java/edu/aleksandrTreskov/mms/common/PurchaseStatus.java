package edu.aleksandrTreskov.mms.common;

import lombok.Getter;

@Getter
public enum PurchaseStatus {
    WAITINGFORPAYMENT("Waiting for payment"),
    WAITINGFORSHIPMENT("Paid, waiting for shipment"),
    SHIPPING("In transit"),
    DELIVERED("Delivered");

    private final String displayValue;

    PurchaseStatus(String displayValue) {
        this.displayValue = displayValue;
    }


}