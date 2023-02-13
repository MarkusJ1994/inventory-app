package com.example.inventory.domain;

import java.util.UUID;

public record State<T>(T state, UUID stateId) {
}
