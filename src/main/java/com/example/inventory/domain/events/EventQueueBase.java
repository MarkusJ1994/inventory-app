package com.example.inventory.domain.events;

import com.example.inventory.aggregator.Step;
import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// S is the type of state
public abstract class EventQueueBase<S> extends ArrayDeque<DomainEvent> {

    private final EventLogRepository eventLogRepository;

    public EventQueueBase(ArrayDeque arrayDeque, EventLogRepository eventLogRepository) {
        super(arrayDeque);
        this.eventLogRepository = eventLogRepository;
    }

    public abstract List<Step<S>> fold();

    @Override
    public boolean add(DomainEvent command) {
        //Log the event
        eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, command.getCommand(), command.payload()));
        return super.add(command);
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Can not bulk add on the EventQueue");
    }

}