package edu.aleksandrTreskov.mms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseAttribute {
    private boolean error;
    private AddressDTO addressDTO;
    private String message;
    private int discountPercent;
}
