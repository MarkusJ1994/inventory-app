package com.example.inventory.domain.items.events;

import com.example.inventory.domain.events.DomainEvent;

public record DeleteItemCommand(String payload) implements DomainEvent<String, Void> {

    @Override
    public String getCommand() {
        return ItemEventCommands.REMOVE_ITEM.name();
    }

}
