package com.example.inventory.domain.items;

import com.example.inventory.data.EventLogRepository;
import com.example.inventory.data.Item;
import com.example.inventory.domain.State;
import com.example.inventory.domain.StateMutator;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.events.EventQueue;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemEventQueue extends EventQueue<Item> {

    private final StateMutator stateMutator;

    public ItemEventQueue(EventLogRepository eventLogRepository, ArrayDeque arrayDeque, StateMutator stateMutator) {
        super(arrayDeque, eventLogRepository);
        this.stateMutator = stateMutator;
    }

    public List<Item> fold(State<List<Item>> currentState) {
        List<Item> items = new ArrayList<>(currentState.state());
        for (DomainEvent command : this) {
            items = stateMutator.applyMutationCommand(command, items);
        }
        return items;
    }

}
