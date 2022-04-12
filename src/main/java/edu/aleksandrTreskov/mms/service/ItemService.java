package edu.aleksandrTreskov.mms.service;

import edu.aleksandrTreskov.mms.dto.ItemDTO;
import edu.aleksandrTreskov.mms.entity.Item;
import edu.aleksandrTreskov.mms.mapstruct.mapper.ItemMapper;
import edu.aleksandrTreskov.mms.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final MessageService messageService;

    public void saveItem(Item item) {
        itemRepository.save(item);
        messageService.sendEmailMessage();
    }

    public List<ItemDTO> findAllItems() {
        List<ItemDTO> items = new ArrayList<>();
        itemRepository.findAllByDeleted().forEach(item -> {
            if (item.getQuantity() > 0)
                items.add(ItemMapper.INSTANCE.toDTO(item));
        });
        return items;

    }

    public ItemDTO findById(long id) {
        return ItemMapper.INSTANCE.toDTO(itemRepository.findById(id));
    }

    public List<String> findCategories() {
        return itemRepository.findAllCategories();
    }

//    public List<ItemDTO> findAllByCategory(String category) {
//        List<ItemDTO> itemDTOS = new ArrayList<>();
//        itemRepository.findAllByCategory(category).forEach(item -> itemDTOS.add(ItemMapper.INSTANCE.toDTO(item)));
//        return itemDTOS;
//    }

    /**
     * Finds top sold items for stats page
     *
     * @return
     */
    public List<Item> findTop10SoldItems() {
        return itemRepository.findTop10OrderBySold();
    }

    public void deleteById(long id) {
        Item item = itemRepository.findById(id);
        item.setDeleted(true);
        itemRepository.save(item);

    }

    /**
     * Loads pages for all items
     *
     * @param pageNo
     * @param pageSize
     * @param sortField
     * @param sortDirection
     * @return
     */
    public Page<Item> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return itemRepository.findAll(pageable);
    }

    /**
     * Loads pages for items with defined category
     *
     * @param pageNo
     * @param pageSize
     * @param sortField
     * @param sortDirection
     * @param category
     * @return
     */
    public Page<Item> findPaginatedCategory(int pageNo, int pageSize, String sortField, String sortDirection, String category) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return itemRepository.findAllByCategory(category, pageable);

    }

    public List<Item> searchByText(String searchText) {
        return itemRepository.searchByText(searchText);
    }


}
