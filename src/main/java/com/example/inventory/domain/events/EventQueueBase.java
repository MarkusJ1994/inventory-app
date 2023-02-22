package com.example.inventory.domain.events;

import com.example.inventory.aggregator.Step;
import com.example.inventory.data.EventLog;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.services.EventService;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.List;

public abstract class EventQueueBase extends ArrayDeque<DomainEvent> {

    private final EventService eventService;

    public EventQueueBase(ArrayDeque arrayDeque, EventService eventService) {
        super(arrayDeque);
        this.eventService = eventService;
        initQueueFromDb();
    }

    private void initQueueFromDb() {
        addAll(eventService.getDomainEvents());
    }

    @Override
    public boolean add(DomainEvent command) {
        //Log the event
        eventService.persistLog(new EventLog(command.getEventId(), LocalDateTime.now(), "Markus", null, command.getCommand(), command.payload()));
        return super.add(command);
    }

    public abstract List<Step<List<Item>>> fold();

    public abstract List<Step<List<Item>>> fold(String stateId);
}
