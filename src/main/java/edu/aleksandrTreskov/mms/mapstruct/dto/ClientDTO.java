package edu.aleksandrTreskov.mms.mapstruct.dto;

import edu.aleksandrTreskov.mms.entity.Address;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String name;
    private String surname;
    private String email;
    private String birthDate;
    private List<Address> addresses;
}
