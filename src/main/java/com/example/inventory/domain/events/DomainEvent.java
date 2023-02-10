package com.example.inventory.domain.events;

public interface DomainEvent<T> {

    String getCommand();

    T getPayload();

}
