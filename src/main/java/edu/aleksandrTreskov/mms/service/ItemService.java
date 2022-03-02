package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.mapstruct.dto.ClientDTO;
import edu.aleksandrTreskov.mms.mapstruct.dto.ItemDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<ItemDTO> getAllItems() {
        List<ItemDTO> items = new ArrayList<>();
        itemRepository.findAll().forEach(item -> {
            if (item.getQuantity() > 0)
                items.add(ItemMapper.INSTANCE.toDTO(item));
        });
        return items;

    }

    public List<ItemDTO> getTop10() {
        List<ItemDTO> items = new ArrayList<>();
        itemRepository.findTop10ByOrderByCategoryAscQuantityDesc().forEach(item ->
                items.add(ItemMapper.INSTANCE.toDTO(item)));
        return items;
    }

    public ItemDTO getItem(long id) {
        return ItemMapper.INSTANCE.toDTO(itemRepository.findById(id));
    }
}
