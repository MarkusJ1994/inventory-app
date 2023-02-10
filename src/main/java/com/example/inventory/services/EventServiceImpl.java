package com.example.inventory.services;

import com.example.inventory.data.EventLog;
import com.example.inventory.data.EventLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventLogRepository eventLogRepository;

    @Override
    public List<EventLog> getEvents() {
        return eventLogRepository.findAll();
    }
}
