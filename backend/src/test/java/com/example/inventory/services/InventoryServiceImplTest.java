package com.example.inventory.services;

import com.example.inventory.aggregator.Step;
import com.example.inventory.data.EventLog;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.items.aggregator.ItemEventAggregator;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;
import com.example.inventory.domain.items.events.AddItemCommand;
import com.example.inventory.domain.items.events.DeleteItemCommand;
import com.example.inventory.domain.items.events.ItemEventQueue;
import com.example.inventory.domain.items.events.UpdateItemCommand;
import com.example.inventory.domain.items.services.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class InventoryServiceImplTest {

    private ItemEventQueue mock() {
        EventService mockedService = Mockito.mock(EventService.class);

        when(mockedService.persistLog(any())).thenReturn(new EventLog());
        when(mockedService.getEvents()).thenReturn(new ArrayList<>());

        ArrayDeque<DomainEvent> queue = new ArrayDeque<>();
        queue.add(new AddItemCommand("eventId1", new AddItemDto("milk", "dairy")));
        queue.add(new AddItemCommand("eventId2", new AddItemDto("butter", "dairy")));

        return new ItemEventQueue(mockedService, queue, new ItemEventAggregator(new InventoryServiceImpl()));
    }

    @Test
    void findItems() {
        ItemEventQueue service = mock();

        List<Step<List<Item>>> steps = service.fold();
        List<Item> state = Step.getMostRecentStep(steps).state();

        assertEquals(2, state.size());
        assertEquals("milk", state.get(0).getName());
        assertEquals("butter", state.get(1).getName());
    }

    @Test
    void addItem() {
        ItemEventQueue service = mock();

        service.add(new AddItemCommand(new AddItemDto("added item", "some category")));
        service.add(new AddItemCommand(new AddItemDto("added item", "some category")));

        List<Step<List<Item>>> steps = service.fold();
        Step<List<Item>> successStep = steps.get(2);

        assertEquals(3, successStep.state().size());
        assertEquals("added item", successStep.state().get(2).getName());
        assertTrue(successStep.result().isResult());

        Step<List<Item>> failStep = steps.get(3);

        assertFalse(failStep.result().isResult());
    }

    @Test
    void updateItem() {
        ItemEventQueue service = mock();

        List<Step<List<Item>>> steps = service.fold();
        List<Item> state = Step.getMostRecentStep(steps).state();
        service.add(new UpdateItemCommand(state.get(0).getId(), new UpdateItemDto("updated item", "dairy")));
        service.add(new UpdateItemCommand("non existing id", new UpdateItemDto("another updated item", "dairy")));

        steps = service.fold();
        Step<List<Item>> successStep = steps.get(2);

        assertEquals(2, successStep.state().size());
        assertEquals("updated item", successStep.state().get(0).getName());
        assertTrue(successStep.result().isResult());

        Step<List<Item>> failStep = steps.get(3);

        assertFalse(failStep.result().isResult());
    }

    @Test
    void removeItem() {
        ItemEventQueue service = mock();

        List<Step<List<Item>>> steps = service.fold();
        List<Item> state = Step.getMostRecentStep(steps).state();
        service.add(new DeleteItemCommand((state.get(0).getId())));
        service.add(new DeleteItemCommand("non existing id"));

        steps = service.fold();
        Step<List<Item>> successStep = steps.get(2);

        assertEquals(1, successStep.state().size());
        assertTrue(successStep.result().isResult());

        Step<List<Item>> failStep = steps.get(3);

        assertFalse(failStep.result().isResult());
    }

}