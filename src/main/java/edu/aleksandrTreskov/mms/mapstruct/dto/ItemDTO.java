package edu.aleksandrTreskov.mms.mapstruct.dto;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private long id;
    private String name;
    private String category;
    private Map<String, String> parameters;
    private int quantity;
    private double price;
    private int sold;
}
