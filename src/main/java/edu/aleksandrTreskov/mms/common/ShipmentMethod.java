package edu.aleksandrTreskov.mms.common;

public enum ShipmentMethod {
    PICKPOINT("PickPoint"),
    DELIVERY("Delivery");
    private final String displayValue;

     ShipmentMethod(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }}
