package edu.aleksandrTreskov.mms.mapstruct.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private long id;
    @NotEmpty(message = "Please provide the country")
    private String country;
    @NotEmpty(message = "Please provide the city")
    private String city;
    @NotEmpty(message = "Please provide the postcode")
    private String postcode;
    @NotEmpty(message = "Please provide the street")
    private String street;
    @NotEmpty(message = "Please provide the building")
    private String building;
    @NotNull(message = "Please provide the flat")

    private int flat;

}
