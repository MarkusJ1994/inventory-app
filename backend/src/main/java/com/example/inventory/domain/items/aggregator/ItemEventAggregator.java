package com.example.inventory.domain.items.aggregator;

import com.example.inventory.aggregator.Aggregator;
import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.events.AddItemCommand;
import com.example.inventory.domain.items.events.DeleteItemCommand;
import com.example.inventory.domain.items.events.UpdateItemCommand;
import com.example.inventory.domain.items.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ItemEventAggregator implements Aggregator<List<Item>> {

    private final InventoryService inventoryService;

    /**
     * This should be the only source of mutations of steps
     */
    @Override
    public <T> Step<List<Item>> aggregateStateFromEvents(DomainEvent<T> event, List<Step<List<Item>>> steps) {
        //wanted to use preview pattern matching but maven couldn't play nice with it
        if (event instanceof AddItemCommand addItemCommand) {
            return inventoryService.addItem(addItemCommand.payload(), Optional.empty(), Step.getMostRecentStep(steps)).toStep(event);
        } else if (event instanceof UpdateItemCommand updateItemCommand) {
            return inventoryService.updateItem(updateItemCommand.payload().id(), updateItemCommand.payload().updateItemDto(), Step.getMostRecentStep(steps)).toStep(event);
        } else if (event instanceof DeleteItemCommand deleteItemCommand) {
            return inventoryService.removeItem(deleteItemCommand.payload(), Step.getMostRecentStep(steps)).toStep(event);
        } else {
            throw new IllegalArgumentException("unhandled item command");
        }
    }

}
