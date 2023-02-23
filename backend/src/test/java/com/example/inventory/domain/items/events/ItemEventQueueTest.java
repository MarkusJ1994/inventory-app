package com.example.inventory.domain.items.events;

import com.example.inventory.aggregator.Step;
import com.example.inventory.data.EventLog;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.aggregator.ItemEventAggregator;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.services.InventoryServiceImpl;
import com.example.inventory.services.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemEventQueueTest {

    private ItemEventQueue mock() {
        EventService mockedService = Mockito.mock(EventService.class);

        Mockito.when(mockedService.persistLog(Mockito.any())).thenReturn(new EventLog());
        Mockito.when(mockedService.getEvents()).thenReturn(new ArrayList<>());

        ArrayDeque<DomainEvent> queue = new ArrayDeque<>();
        queue.add(new AddItemCommand("eventId1", new AddItemDto("milk", "dairy")));
        queue.add(new AddItemCommand("eventId2", new AddItemDto("butter", "dairy")));
        queue.add(new AddItemCommand("eventId3", new AddItemDto("dn", "newspaper")));

        return new ItemEventQueue(mockedService, queue, new ItemEventAggregator(new InventoryServiceImpl()));
    }

    @Test
    void fold() {
        ItemEventQueue queue = mock();

        //fold to eventId2
        List<Step<List<Item>>> steps = queue.fold("eventId2");

        assertEquals(2, steps.size());
        assertEquals(2, steps.get(1).state().size());
        assertEquals("butter", steps.get(1).state().get(1).getId());

        //no id or non matching id will fold all steps
        List<Step<List<Item>>> allSteps = queue.fold("");

        assertEquals(3, allSteps.size());
        assertEquals(3, allSteps.get(2).state().size());
    }
}