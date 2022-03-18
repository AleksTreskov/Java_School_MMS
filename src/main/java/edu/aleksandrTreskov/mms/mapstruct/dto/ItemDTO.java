package edu.aleksandrTreskov.mms.mapstruct.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private long id;
    @NotEmpty(message = "Please provide the name")
    private String name;
    @NotEmpty(message = "Please provide the category")
    private String category;
    private String description;
    @NotEmpty(message = "Please provide the brand")
    private String brand;
    @NotEmpty(message = "Please provide the model")
    private String model;
    @NotNull(message = "Please provide the weight")
    private double weight;
    private int quantity;
    @NotNull(message = "Please provide the price")
    private double price;
    private int sold;
}
