package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.dto.AddItemDto;

public record AddItemCommand(AddItemDto payload) implements DomainEvent<AddItemDto> {

    @Override
    public String getCommand() {
        return ItemEventCommands.ADD_ITEM.name();
    }
}
