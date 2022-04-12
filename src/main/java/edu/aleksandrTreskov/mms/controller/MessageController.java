package edu.aleksandrTreskov.mms.controller;

import edu.aleksandrTreskov.mms.dto.ItemDTO;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final ItemService itemService;
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;

    @GetMapping("/api/message")
    public List<ItemDTO> getInformationForStand() {
        List<ItemDTO> itemDTOs = new ArrayList<>();
        itemService.findTop10SoldItems().forEach(item -> itemDTOs.add(itemMapper.toDTO(item)));
        return itemDTOs;
    }

}
