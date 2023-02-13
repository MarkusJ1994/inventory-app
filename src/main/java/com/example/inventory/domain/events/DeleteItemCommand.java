package com.example.inventory.domain.events;

public record DeleteItemCommand(String payload) implements DomainEvent<String, Void> {

    @Override
    public String getCommand() {
        return "DELETE_ITEM";
    }

}
