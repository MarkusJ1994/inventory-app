package com.example.inventory.services;

import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    ItemDto findItemById(String id);

    List<ItemDto> findItems();

    ItemDto addItem(AddItemDto itemDto, Optional<String> id);

    ItemDto updateItem(String id, UpdateItemDto itemDto);

    void removeItem(String id);

}
