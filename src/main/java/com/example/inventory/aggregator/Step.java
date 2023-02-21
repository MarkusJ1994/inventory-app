package com.example.inventory.aggregator;

import java.util.List;
import java.util.Optional;

public record Step<T>(T state, Result result) {

    public static <T> Optional<Step<T>> getMostRecentStep(List<Step<T>> steps) {
        return steps != null && !steps.isEmpty() ? Optional.of(steps.get(steps.size() - 1)) : Optional.empty();
    }
}
