package com.example.inventory.services;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemRepository;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.exceptions.DuplicateException;
import com.example.inventory.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ItemRepository itemRepository;


    @Override
    public ItemDto findItemById(String id) {
        Optional<Item> foundItem = itemRepository.findById(id);
        if (foundItem.isPresent()) {
            return itemToItemDto(foundItem.get());
        } else {
            throw new NotFoundException(String.format("Item with id [%s] not found", id));
        }
    }

    @Override
    public List<ItemDto> findItems() {
        return itemRepository.findAll().stream().map(InventoryServiceImpl::itemToItemDto).toList();
    }

    @Override
    public ItemDto addItem(ItemDto itemDto) {
        Item item = itemDtoToItem(itemDto);
        if (itemRepository.findById(item.getId()).isEmpty()) {
            return itemToItemDto(itemRepository.save(item));
        } else {
            throw new DuplicateException(String.format("Item with the id [%s] already exist", item.getId()));
        }
    }

    @Override
    public ItemDto updateItem(String id, ItemDto itemDto) {
        Item item = itemDtoToItem(itemDto);
        if (itemRepository.findById(item.getId()).isPresent()) {
            return itemToItemDto(itemRepository.save(item));
        } else {
            throw new NotFoundException(String.format("Item with the id [%s] already exist", item.getId()));
        }
    }

    @Override
    public void removeItem(String id) {
        itemRepository.deleteById(id);
    }

    static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getName(), itemDto.getName());
    }

    static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getName());
    }

}
