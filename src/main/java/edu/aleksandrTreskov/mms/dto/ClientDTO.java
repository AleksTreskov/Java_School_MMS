package edu.aleksandrTreskov.mms.dto;

import edu.aleksandrTreskov.mms.entity.Address;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
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
    private String phoneNumber;
    private String birthDate;
    private List<Address> addresses;
}
