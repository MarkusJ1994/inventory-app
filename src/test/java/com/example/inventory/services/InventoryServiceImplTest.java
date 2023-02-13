package com.example.inventory.services;

import com.example.inventory.data.EventLogRepository;
import com.example.inventory.data.Item;
import com.example.inventory.domain.State;
import com.example.inventory.domain.StateMutator;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;
import com.example.inventory.domain.events.AddItemCommand;
import com.example.inventory.domain.events.DeleteItemCommand;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.events.UpdateItemCommand;
import com.example.inventory.domain.items.ItemEventQueue;
import com.example.inventory.domain.items.events.EventQueue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryServiceImplTest {

    private ItemEventQueue mock() {
        EventLogRepository mockedRepository = Mockito.mock(EventLogRepository.class);

        Mockito.when(mockedRepository.save(Mockito.any())).thenReturn(Mockito.any());

        ArrayDeque<DomainEvent> queue = new ArrayDeque<>();
        queue.add(new AddItemCommand(new AddItemDto("milk", "dairy")));
        queue.add(new AddItemCommand(new AddItemDto("butter", "dairy")));

        return new ItemEventQueue(mockedRepository, queue, new StateMutator(new InventoryServiceImpl()));
    }

    @Test
    void findItemById() {
        EventQueue service = mock();
//        assertEquals(constructItemDto("milk", "milk"), service.fold("milk"));
//        assertThrows(NotFoundException.class, () -> service.findItemById("bread"));
    }

    @Test
    void findItems() {
        ItemEventQueue service = mock();
        List<Item> state = service.fold(new State<>(List.of(), UUID.randomUUID()));
        assertEquals(2, state.size());
        assertEquals(state.get(0).getName(), "milk");
        assertEquals(state.get(1).getName(), "butter");
    }

    @Test
    void addItem() {
        ItemEventQueue service = mock();
        service.add(new AddItemCommand(new AddItemDto("added item", "some category")));
        List<Item> state = service.fold(new State<>(List.of(), UUID.randomUUID()));
        assertEquals(3, state.size());
        assertEquals(state.get(2).getName(), "added item");
//        assertThrows(DuplicateException.class, () -> service.add(new AddItemCommand(new AddItemDto("added item", "some category"))));
    }

    @Test
    void updateItem() {
        ItemEventQueue service = mock();
        service.add(new UpdateItemCommand(service.fold(new State<>(List.of(), UUID.randomUUID())).get(0).getId(), new UpdateItemDto("updated item", "some category")));
        List<Item> updatedState = service.fold(new State<>(List.of(), UUID.randomUUID()));
        assertEquals(2, updatedState.size());
        assertEquals(updatedState.get(0).getName(), "updated item");
//        assertThrows(NotFoundException.class, () -> service.updateItem("sausage", UpdateItemDto.fromItemDto(constructItemDto("milk", "butter"))));
    }

    @Test
    void removeItem() {
        ItemEventQueue service = mock();
        service.add(new DeleteItemCommand((service.fold(new State<>(List.of(), UUID.randomUUID())).get(0).getId())));
        List<Item> updatedState = service.fold(new State<>(List.of(), UUID.randomUUID()));
        assertEquals(1, updatedState.size());
        assertEquals(updatedState.get(0).getName(), "butter");
//        assertThrows(NotFoundException.class, () -> service.removeItem("bread"));
    }

    protected ItemDto constructItemDto(String id, String name) {
        return new ItemDto(id, name, "some category");
    }

    protected Item constructItem(String id, String name) {
        return new Item(id, name, "some category");
    }
}