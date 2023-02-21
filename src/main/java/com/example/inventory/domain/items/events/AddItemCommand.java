package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;

public record AddItemCommand(AddItemDto payload) implements DomainEvent<AddItemDto, ItemDto> {

    @Override
    public String getCommand() {
        return ItemEventCommands.ADD_ITEM.name();
    }
}
