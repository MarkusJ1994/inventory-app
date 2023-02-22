package com.example.inventory.domain.events;

import com.example.inventory.aggregator.Step;
import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.services.EventServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.List;

// S is the type of state
public abstract class EventQueueBase extends ArrayDeque<DomainEvent> {

    private final EventLogRepository eventLogRepository;

    public EventQueueBase(ArrayDeque arrayDeque, EventLogRepository eventLogRepository) {
        super(arrayDeque);
        this.eventLogRepository = eventLogRepository;
        initQueueFromDb();
    }

    private void initQueueFromDb() {
        addAll(eventLogRepository.findAll().stream().map(EventServiceImpl::logToDomainEvent).toList());
    }

    @Override
    public boolean add(DomainEvent command) {
        //Log the event
        eventLogRepository.save(new EventLog(command.getEventId(), LocalDateTime.now(), "Markus", null, command.getCommand(), command.payload()));
        return super.add(command);
    }

    public abstract List<Step<List<Item>>> fold();

    public abstract List<Step<List<Item>>> fold(String stateId);
}
