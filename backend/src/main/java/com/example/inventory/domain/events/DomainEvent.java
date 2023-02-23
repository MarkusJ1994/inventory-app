package com.example.inventory.domain.events;

//Add more stuff here, like reason/cause?
public abstract class DomainEvent<T> {

    private final String eventId;
    private final T payload;

    public DomainEvent(String id, T payload) {
        this.eventId = id;
        this.payload = payload;
    }

    public String getEventId() {
        return eventId;
    }

    public T payload() {
        return payload;
    }

    public abstract String getCommand();

}
