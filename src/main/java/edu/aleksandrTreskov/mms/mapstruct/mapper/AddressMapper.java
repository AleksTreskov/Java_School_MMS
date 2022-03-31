package edu.aleksandrTreskov.mms.mapstruct.mapper;

import edu.aleksandrTreskov.mms.entity.Address;
import edu.aleksandrTreskov.mms.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    AddressDTO toDTO(Address address);
    Address toAddress(AddressDTO addressDTO);
}
