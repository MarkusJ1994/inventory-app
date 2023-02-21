package com.example.inventory.domain.events;

import com.example.inventory.domain.items.events.AddItemCommand;
import com.example.inventory.domain.items.events.DeleteItemCommand;
import com.example.inventory.domain.items.events.UpdateItemCommand;

//Add more stuff here, like reason/cause?
public sealed interface DomainEvent<T, U> permits AddItemCommand, DeleteItemCommand, UpdateItemCommand {

    String getCommand();

    T payload();

}
