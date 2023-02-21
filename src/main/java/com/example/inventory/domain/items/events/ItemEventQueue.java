package com.example.inventory.domain.items.events;

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
public class ItemEventQueue extends EventQueueBase<Item> {

    private final ItemEventAggregator itemEventAggregator;

    public ItemEventQueue(EventLogRepository eventLogRepository, ArrayDeque arrayDeque, ItemEventAggregator itemEventAggregator) {
        super(arrayDeque, eventLogRepository);
        this.itemEventAggregator = itemEventAggregator;
    }

    @Override
    public List<Item> fold() {
        List<Item> items = new ArrayList<>();
        for (DomainEvent command : this) {
            items = itemEventAggregator.aggregateStateFromEvents(command, items);
        }
        return items;
    }

}
