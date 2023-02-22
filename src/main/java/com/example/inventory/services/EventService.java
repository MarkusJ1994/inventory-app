package com.example.inventory.services;

import com.example.inventory.data.EventLog;
import com.example.inventory.domain.events.DomainEvent;

import java.util.List;

public interface EventService {

    List<EventLog> getEvents();

    List<DomainEvent> getDomainEvents();

    EventLog persistLog(EventLog log);

}
