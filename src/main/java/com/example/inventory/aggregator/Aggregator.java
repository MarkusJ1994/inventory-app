package com.example.inventory.aggregator;

import com.example.inventory.domain.events.DomainEvent;

import java.util.List;

public interface Aggregator {

    <T, U> List<U> aggregateStateFromEvents(DomainEvent<T, U> event, List<U> state);

}
