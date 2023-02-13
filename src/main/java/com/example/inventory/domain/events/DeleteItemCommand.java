package com.example.inventory.domain.events;

import com.example.inventory.domain.EventCommands;

public record DeleteItemCommand(String payload) implements DomainEvent<String, Void> {

    @Override
    public String getCommand() {
        return EventCommands.REMOVE_ITEM.name();
    }

}
