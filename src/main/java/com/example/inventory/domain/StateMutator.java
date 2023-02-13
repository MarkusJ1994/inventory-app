package com.example.inventory.domain;

import com.example.inventory.data.Item;
import com.example.inventory.domain.events.AddItemCommand;
import com.example.inventory.domain.events.DeleteItemCommand;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.events.UpdateItemCommand;
import com.example.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StateMutator {

    private final InventoryService inventoryService;

    /**
     * Given a state and an event, performs a mutation. This should be the only source of mutations of state
     *
     * @param event
     * @param state
     * @param <T>
     * @param <U>
     * @return
     */
    public <T, U> List<U> applyMutationCommand(DomainEvent<T, U> event, List<U> state) {
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
