package com.example.inventory.aggregator;

import com.example.inventory.domain.events.DomainEvent;

import java.util.List;

public interface Aggregator<S> {

    <T> Step<S> aggregateStateFromEvents(DomainEvent<T> event, List<Step<S>> steps);

}
