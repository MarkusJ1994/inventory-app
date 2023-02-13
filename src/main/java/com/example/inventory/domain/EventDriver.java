package com.example.inventory.domain;

import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.events.AddItemCommand;
import com.example.inventory.domain.events.DeleteItemCommand;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.events.UpdateItemCommand;
import com.example.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EventDriver {

    private final InventoryService inventoryService;

    private final EventLogRepository eventLogRepository;

    public <T, U> U applyCommand(DomainEvent<T, U> event) {
        switch (event) {
            case AddItemCommand addItemCommand -> {
                ItemDto itemDto = inventoryService.addItem(addItemCommand.payload(), Optional.empty());
                //Log the event
                eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, addItemCommand.getCommand(), addItemCommand.payload()));
                return (U) itemDto;
            }
            case UpdateItemCommand updateItemCommand -> {
                ItemDto itemDto = inventoryService.updateItem(updateItemCommand.payload().id(), updateItemCommand.payload().updateItemDto());
                //Log the event
                eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, updateItemCommand.getCommand(), updateItemCommand.payload()));
                return (U) itemDto;
            }
            case DeleteItemCommand addItemCommand -> {
                inventoryService.removeItem(addItemCommand.payload());
                //Log the event
                eventLogRepository.save(new EventLog(UUID.randomUUID(), LocalDateTime.now(), "Markus", null, addItemCommand.getCommand(), addItemCommand.payload()));
                return (U) null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + event);
        }
    }

}
