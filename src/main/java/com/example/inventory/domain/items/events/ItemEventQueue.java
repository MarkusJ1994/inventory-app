package com.example.inventory.domain.items.events;

import com.example.inventory.aggregator.Step;
import com.example.inventory.data.EventLogRepository;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.aggregator.ItemEventAggregator;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.events.EventQueueBase;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemEventQueue extends EventQueueBase<List<Item>> {

    private final ItemEventAggregator itemEventAggregator;

    public ItemEventQueue(EventLogRepository eventLogRepository, ArrayDeque arrayDeque, ItemEventAggregator itemEventAggregator) {
        super(arrayDeque, eventLogRepository);
        this.itemEventAggregator = itemEventAggregator;
    }

    @Override
    public List<Step<List<Item>>> fold() {
        List<Step<List<Item>>> steps = new ArrayList<>();
        for (DomainEvent command : this) {
            Step step = itemEventAggregator.aggregateStateFromEvents(command, steps);
            steps.add(step);
        }
        return steps;
    }

}
