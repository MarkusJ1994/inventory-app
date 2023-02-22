package com.example.inventory.aggregator;

import com.example.inventory.domain.events.DomainEvent;

import java.util.List;

public record Step<T>(DomainEvent event, Result result, T state) {

    public static <T> Step<T> getMostRecentStep(List<Step<T>> steps) {
        return steps != null && !steps.isEmpty() ? steps.get(steps.size() - 1) : null;
    }
}
