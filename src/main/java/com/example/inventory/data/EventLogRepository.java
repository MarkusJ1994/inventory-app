package com.example.inventory.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EventLogRepository extends MongoRepository<EventLog, UUID> {
}
