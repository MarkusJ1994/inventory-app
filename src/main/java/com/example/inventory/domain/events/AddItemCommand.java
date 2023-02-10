package com.example.inventory.domain.events;

import com.example.inventory.domain.dto.ItemDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddItemCommand implements DomainEvent<ItemDto> {

    private final ItemDto item;

    @Override
    public String getCommand() {
        return "ADD_ITEM";
    }

    @Override
    public ItemDto getPayload() {
        return item;
    }
}
