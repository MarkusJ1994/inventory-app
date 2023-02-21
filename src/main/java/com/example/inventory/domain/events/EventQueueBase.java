package com.example.inventory.domain.events;

import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.items.events.ItemEventCommands;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class EventQueueBase<T> extends ArrayDeque<DomainEvent> {

    private final EventLogRepository eventLogRepository;

    public EventQueueBase(ArrayDeque arrayDeque, EventLogRepository eventLogRepository) {
        super(arrayDeque);
        this.eventLogRepository = eventLogRepository;
    }

    public abstract List<T> fold();

    @Override
    public boolean add(DomainEvent command) {
        //Log the event
        eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, ItemEventCommands.ADD_ITEM.name(), command.payload()));
        return super.add(command);
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Can not bulk add on the EventQueue");
    }

}
