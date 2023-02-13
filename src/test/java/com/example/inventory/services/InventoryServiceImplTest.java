package com.example.inventory.services;

import com.example.inventory.data.Item;
import com.example.inventory.data.ItemRepository;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;
import com.example.inventory.domain.exceptions.DuplicateException;
import com.example.inventory.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceImplTest {

    private InventoryService mock() {
        ItemRepository mockedRepository = Mockito.mock(ItemRepository.class);

        Mockito.when(mockedRepository.findAll()).thenReturn(List.of(constructItem("1", "milk"), constructItem("2", "eggs")));

        Mockito.when(mockedRepository.findById("milk")).thenReturn(Optional.of(constructItem("milk", "milk")));
        Mockito.when(mockedRepository.findById("eggs")).thenReturn(Optional.of(constructItem("eggs", "eggs")));

        Mockito.when(mockedRepository.save(constructItem("random_id", "bread"))).thenReturn(constructItem("random_id", "bread"));
        Mockito.when(mockedRepository.save(constructItem("milk", "butter"))).thenReturn(constructItem("milk", "butter"));

        return new InventoryServiceImpl(mockedRepository);
    }

    @Test
    void findItemById() {
        InventoryService service = mock();
        assertEquals(constructItemDto("milk", "milk"), service.findItemById("milk"));
        assertThrows(NotFoundException.class, () -> service.findItemById("bread"));
    }

    @Test
    void findItems() {
        InventoryService service = mock();
        assertEquals(List.of(constructItemDto("1", "milk"), constructItemDto("2", "eggs")), service.findItems());
    }

    @Test
    void addItem() {
        InventoryService service = mock();
        assertEquals(
                constructItemDto("random_id", "bread"),
                service.addItem(AddItemDto.fromItemDto(constructItemDto(null, "bread")), Optional.of("random_id")));
        assertThrows(DuplicateException.class, () -> service.addItem(AddItemDto.fromItemDto(constructItemDto(null, "milk")), Optional.of("milk")));
    }

    @Test
    void updateItem() {
        InventoryService service = mock();
        assertEquals(
                constructItemDto("milk", "butter"),
                service.updateItem("milk", UpdateItemDto.fromItemDto(constructItemDto(null, "butter"))));
        assertThrows(NotFoundException.class, () -> service.updateItem("sausage", UpdateItemDto.fromItemDto(constructItemDto("milk", "butter"))));
    }

    @Test
    void removeItem() {
        InventoryService service = mock();
        assertDoesNotThrow(() -> service.removeItem("milk"));
        assertThrows(NotFoundException.class, () -> service.removeItem("bread"));
    }

    protected ItemDto constructItemDto(String id, String name) {
        return new ItemDto(id, name, "some category");
    }

    protected Item constructItem(String id, String name) {
        return new Item(id, name, "some category");
    }
}