package com.example.inventory.domain;

import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.events.AddItemCommand;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EventDriver {

    private final InventoryService inventoryService;

    private final EventLogRepository eventLogRepository;

    public <T> T applyCommand(DomainEvent<T> event) {
        switch (event) {
            case AddItemCommand addItemCommand -> {
                ItemDto itemDto = inventoryService.addItem(addItemCommand.getPayload());
                //Log the event
                eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, addItemCommand.getCommand(), addItemCommand.getPayload()));
                return (T) itemDto;
            }
            default -> throw new IllegalStateException("Unexpected value: " + event);
        }
    }

}
