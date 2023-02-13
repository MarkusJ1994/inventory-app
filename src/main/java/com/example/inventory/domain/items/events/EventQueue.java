package com.example.inventory.domain.items.events;

import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.EventCommands;
import com.example.inventory.domain.State;
import com.example.inventory.domain.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class EventQueue<T> extends ArrayDeque<DomainEvent> {

    private final EventLogRepository eventLogRepository;

    public EventQueue(ArrayDeque arrayDeque, EventLogRepository eventLogRepository) {
        super(arrayDeque);
        this.eventLogRepository = eventLogRepository;
    }

    public List<T> fold(State<List<T>> state) {
        throw new UnsupportedOperationException("Fold not implemented");
    }

    @Override
    public boolean add(DomainEvent command) {
        //Log the event
        eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, EventCommands.ADD_ITEM.name(), command.payload()));
        return super.add(command);
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Can not bulk add on the EventQueue");
    }

}
