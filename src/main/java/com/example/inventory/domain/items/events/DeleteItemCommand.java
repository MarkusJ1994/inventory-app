package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;

import java.util.UUID;

public class DeleteItemCommand extends DomainEvent<String> {

    public DeleteItemCommand(String eventId, String payload) {
        super(eventId, payload);
    }

    public DeleteItemCommand(String payload) {
        this(UUID.randomUUID().toString(), payload);
    }

    @Override
    public String getCommand() {
        return ItemEventCommands.REMOVE_ITEM.name();
    }

}
