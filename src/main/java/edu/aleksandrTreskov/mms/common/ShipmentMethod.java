package edu.aleksandrTreskov.mms.common;

public enum ShipmentMethod {
    SAMOVYVOZ("Самовывоз"),
    COURIER("Курьер");
    private final String displayValue;

     ShipmentMethod(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }}
