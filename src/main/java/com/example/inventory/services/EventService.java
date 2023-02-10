package com.example.inventory.services;

import com.example.inventory.data.EventLog;

import java.util.List;

public interface EventService {

    List<EventLog> getEvents();

}
