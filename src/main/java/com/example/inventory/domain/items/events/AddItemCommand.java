package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.dto.AddItemDto;

import java.util.UUID;

public class AddItemCommand extends DomainEvent<AddItemDto> {

    public AddItemCommand(String eventId, AddItemDto payload) {
        super(eventId, payload);
    }

    public AddItemCommand(AddItemDto payload) {
        this(UUID.randomUUID().toString(), payload);
    }

    @Override
    public String getCommand() {
        return ItemEventCommands.ADD_ITEM.name();
    }
}
