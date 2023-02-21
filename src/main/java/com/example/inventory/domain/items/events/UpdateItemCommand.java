package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;

public record UpdateItemCommand(
        UpdateItemCommand.UpdateItemCommandPayload payload) implements DomainEvent<UpdateItemCommand.UpdateItemCommandPayload, ItemDto> {

    public UpdateItemCommand(String id, UpdateItemDto itemDto) {
        this(new UpdateItemCommandPayload(id, itemDto));
    }

    @Override
    public String getCommand() {
        return ItemEventCommands.UPDATE_ITEM.name();
    }

    public record UpdateItemCommandPayload(String id, UpdateItemDto updateItemDto) {
    }
}
