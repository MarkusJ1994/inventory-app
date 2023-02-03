package com.example.inventory.services;

import com.example.inventory.domain.dto.ItemDto;

import java.util.List;

public interface InventoryService {

    ItemDto findItemById(String id);

    List<ItemDto> findItems();

    ItemDto addItem(ItemDto itemDto);

    ItemDto updateItem(String id, ItemDto itemDto);

    void removeItem(String id);

}
