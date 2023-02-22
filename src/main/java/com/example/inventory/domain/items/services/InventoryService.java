package com.example.inventory.domain.items.services;

import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;
import com.example.inventory.domain.items.services.InventoryServiceImpl.InventoryMutationResult;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    ItemDto findItemById(String id, List<Item> state);

    InventoryMutationResult addItem(AddItemDto itemDto, Optional<String> id);

    InventoryMutationResult addItem(AddItemDto itemDto, Optional<String> id, Step<List<Item>> previousStep);

    InventoryMutationResult updateItem(String id, UpdateItemDto itemDto);

    InventoryMutationResult updateItem(String id, UpdateItemDto itemDto, Step<List<Item>> previousStep);

    InventoryMutationResult removeItem(String id);

    InventoryMutationResult removeItem(String id, Step<List<Item>> previousStep);

    static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getCategory());
    }

    static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getCategory());
    }

}
