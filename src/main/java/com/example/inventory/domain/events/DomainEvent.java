package com.example.inventory.domain.events;

public interface DomainEvent<T, U> {

    String getCommand();

    T payload();

}
