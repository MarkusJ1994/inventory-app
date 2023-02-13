package com.example.inventory.services;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemRepository;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;
import com.example.inventory.domain.exceptions.DuplicateException;
import com.example.inventory.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ItemDto addItem(AddItemDto itemDto, Optional<String> id) {
        Item item = itemDtoToItem(itemDto.toItemDto());
        item.setId(id.orElse(UUID.randomUUID().toString()));
        if (itemRepository.findById(item.getId()).isEmpty()) {
            return itemToItemDto(itemRepository.save(item));
        } else {
            throw new DuplicateException(String.format("Item with the id [%s] already exist", item.getId()));
        }
    }

    @Override
    public ItemDto updateItem(String id, UpdateItemDto itemDto) {
        Item item = itemDtoToItem(itemDto.toItemDto(id));
        if (itemRepository.findById(id).isPresent()) {
            return itemToItemDto(itemRepository.save(item));
        } else {
            throw new NotFoundException(String.format("Item with the id [%s] does not exist", id));
        }
    }

    @Override
    public void removeItem(String id) {
        if (itemRepository.findById(id).isPresent()) {
            itemRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format("Item with the id [%s] does not exist", id));
        }
    }

    static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getCategory());
    }

    static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getCategory());
    }

}
