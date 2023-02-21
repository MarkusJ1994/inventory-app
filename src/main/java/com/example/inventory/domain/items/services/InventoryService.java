package com.example.inventory.domain.items.services;

import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    ItemDto findItemById(String id, List<Item> state);

    void addItem(AddItemDto itemDto, Optional<String> id, List<Item> state);

    void updateItem(String id, UpdateItemDto itemDto, List<Item> state);

    void removeItem(String id, List<Item> state);

    static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getCategory());
    }

    static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getCategory());
    }

}
