package com.example.inventory.aggregator;

import com.example.inventory.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;

public record Step<T>(DomainEvent event, Result result, T state) {

    public static <T> Optional<Step<T>> getMostRecentStep(List<Step<T>> steps) {
        return steps != null && !steps.isEmpty() ? Optional.of(steps.get(steps.size() - 1)) : Optional.empty();
    }
}
