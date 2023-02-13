package com.example.inventory.domain.events;

//Add more stuff here, like reason/cause?
public sealed interface DomainEvent<T, U> permits AddItemCommand, DeleteItemCommand, UpdateItemCommand {

    String getCommand();

    T payload();

}
