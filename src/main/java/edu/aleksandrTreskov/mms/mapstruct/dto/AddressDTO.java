package edu.aleksandrTreskov.mms.mapstruct.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String country;
    private String city;
    private String index;
    private String street;
    private String house;
    private int flat;
}
