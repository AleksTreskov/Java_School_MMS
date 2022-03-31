package edu.aleksandrTreskov.mms.mapstruct.mapper;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDTO toDTO(Item item);

    Item toItem(ItemDTO itemDto);
}
