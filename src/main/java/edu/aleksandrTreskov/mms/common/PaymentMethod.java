package edu.aleksandrTreskov.mms.common;

public enum PaymentMethod {
    CASH("Cash"),
    CARD("Credit Card");
    private final String displayValue;

    PaymentMethod(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
