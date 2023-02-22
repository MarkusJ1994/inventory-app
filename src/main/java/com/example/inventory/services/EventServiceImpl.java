package com.example.inventory.services;

import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.events.AddItemCommand;
import com.example.inventory.domain.items.events.DeleteItemCommand;
import com.example.inventory.domain.items.events.ItemEventCommands;
import com.example.inventory.domain.items.events.UpdateItemCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventLogRepository eventLogRepository;

    @Override
    public List<EventLog> getEvents() {
        return eventLogRepository.findAll();
    }

    @Override
    public List<DomainEvent> getDomainEvents() {
        return getEvents().stream().map(EventServiceImpl::logToDomainEvent).toList();
    }

    public static DomainEvent logToDomainEvent(EventLog eventLog) {
        return switch (ItemEventCommands.valueOf(eventLog.getCommand())) {
            case ADD_ITEM -> new AddItemCommand(eventLog.getId(), (AddItemDto) eventLog.getData());
            case UPDATE_ITEM -> {
                UpdateItemCommand.UpdateItemCommandPayload data = (UpdateItemCommand.UpdateItemCommandPayload) eventLog.getData();
                yield new UpdateItemCommand(eventLog.getId(), data.id(), data.updateItemDto());
            }
            case REMOVE_ITEM -> new DeleteItemCommand(eventLog.getId(), (String) eventLog.getData());
        };
    }
}
