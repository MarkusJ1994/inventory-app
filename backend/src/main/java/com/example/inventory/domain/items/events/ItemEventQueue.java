package com.example.inventory.domain.items.events;

import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.events.EventQueueBase;
import com.example.inventory.domain.items.aggregator.ItemEventAggregator;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.services.EventService;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemEventQueue extends EventQueueBase {

    private final ItemEventAggregator itemEventAggregator;

    public ItemEventQueue(EventService eventService, ArrayDeque arrayDeque, ItemEventAggregator itemEventAggregator) {
        super(arrayDeque, eventService);
        this.itemEventAggregator = itemEventAggregator;
    }

    @Override
    public List<Step<List<Item>>> fold() {
        return fold(null);
    }

    @Override
    public List<Step<List<Item>>> fold(String eventId) {
        List<Step<List<Item>>> steps = new ArrayList<>();
        for (DomainEvent command : this) {
            Step<List<Item>> step = itemEventAggregator.aggregateStateFromEvents(command, steps);
            steps.add(step);
            if (command.getEventId().equals(eventId)) {
                //we have traversed the event stream until the desired point
                break;
            }
        }
        return steps;
    }

}
