package com.example.inventory.domain.items.aggregator;

import com.example.inventory.aggregator.Aggregator;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.events.AddItemCommand;
import com.example.inventory.domain.items.events.DeleteItemCommand;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.events.UpdateItemCommand;
import com.example.inventory.domain.items.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ItemEventAggregator implements Aggregator {

    private final InventoryService inventoryService;

    /**
     * This should be the only source of mutations of state
     *
     * @param event
     * @param state
     * @param <T>
     * @param <U>
     * @return
     */
    @Override
    public <T, U> List<U> aggregateStateFromEvents(DomainEvent<T, U> event, List<U> state) {
        return switch (event) {
            case AddItemCommand addItemCommand -> {
                ArrayList<U> stateCopy = new ArrayList<>(state);
                inventoryService.addItem(addItemCommand.payload(), Optional.empty(), (List<Item>) stateCopy);
                yield stateCopy;
            }
            case UpdateItemCommand updateItemCommand -> {
                ArrayList<U> stateCopy = new ArrayList<>(state);
                inventoryService.updateItem(updateItemCommand.payload().id(), updateItemCommand.payload().updateItemDto(), (List<Item>) stateCopy);
                yield stateCopy;
            }
            case DeleteItemCommand deleteItemCommand -> {
                ArrayList<U> stateCopy = new ArrayList<>(state);
                inventoryService.removeItem(deleteItemCommand.payload(), (List<Item>) stateCopy);
                yield stateCopy;
            }
        };
    }

}
