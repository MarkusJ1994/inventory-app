package com.example.inventory.domain.events;

import com.example.inventory.domain.EventCommands;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;

public record AddItemCommand(AddItemDto payload) implements DomainEvent<AddItemDto, ItemDto> {

    @Override
    public String getCommand() {
        return EventCommands.ADD_ITEM.name();
    }
}
