package com.example.inventory.domain.events;

//Add more stuff here, like reason/cause?
public interface DomainEvent<T> {

    String getCommand();

    T payload();

}
