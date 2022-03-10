package edu.aleksandrTreskov.mms.common;

public enum PaymentMethod {
    CASH("Наличные"),
    CARD("Банковская карта");
    private final String displayValue;

    PaymentMethod(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
