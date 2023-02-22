package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.dto.UpdateItemDto;

import java.util.UUID;

public class UpdateItemCommand extends DomainEvent<UpdateItemCommand.UpdateItemCommandPayload> {

    public UpdateItemCommand(String eventId, String id, UpdateItemDto itemDto) {
        super(eventId, new UpdateItemCommandPayload(id, itemDto));
    }

    public UpdateItemCommand(String id, UpdateItemDto itemDto) {
        this(UUID.randomUUID().toString(), id, itemDto);
    }

    @Override
    public String getCommand() {
        return ItemEventCommands.UPDATE_ITEM.name();
    }

    public record UpdateItemCommandPayload(String id, UpdateItemDto updateItemDto) {
    }
}
