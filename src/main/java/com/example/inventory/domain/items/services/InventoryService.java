package com.example.inventory.domain.items.services;

import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    ItemDto findItemById(String id, List<Item> state);

    Step<List<Item>> addItem(AddItemDto itemDto, Optional<String> id, Optional<Step<List<Item>>> previousStep);

    Step<List<Item>> updateItem(String id, UpdateItemDto itemDto, Optional<Step<List<Item>>> previousStep);

    Step<List<Item>> removeItem(String id, Optional<Step<List<Item>>> previousStep);

    static Item itemDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getCategory());
    }

    static ItemDto itemToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getCategory());
    }

}
