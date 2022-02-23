package edu.aleksandrTreskov.mms.mapstruct.mapper;

import edu.aleksandrTreskov.mms.entity.Purchase;
import edu.aleksandrTreskov.mms.mapstruct.dto.PurchaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

    PurchaseDTO toDTO(Purchase purchase);
}
