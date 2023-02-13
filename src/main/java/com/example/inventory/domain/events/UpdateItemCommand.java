package com.example.inventory.domain.events;

import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;

public record UpdateItemCommand(
        UpdateItemCommand.UpdateItemCommandPayload payload) implements DomainEvent<UpdateItemCommand.UpdateItemCommandPayload, ItemDto> {

    public UpdateItemCommand(String id, UpdateItemDto itemDto) {
        this(new UpdateItemCommandPayload(id, itemDto));
    }

    @Override
    public String getCommand() {
        return "UPDATE_ITEM";
    }

    public record UpdateItemCommandPayload(String id, UpdateItemDto updateItemDto) {
    }
}
