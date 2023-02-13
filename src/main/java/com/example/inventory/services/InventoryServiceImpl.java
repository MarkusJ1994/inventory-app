package com.example.inventory.services;

import com.example.inventory.data.Item;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;
import com.example.inventory.domain.exceptions.DuplicateException;
import com.example.inventory.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.inventory.services.InventoryService.itemDtoToItem;
import static com.example.inventory.services.InventoryService.itemToItemDto;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private Optional<Item> findItem(String id, List<Item> state) {
        return state.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public ItemDto findItemById(String id, List<Item> state) {
        Optional<Item> foundItem = findItem(id, state);
        if (foundItem.isPresent()) {
            return itemToItemDto(foundItem.get());
        } else {
            throw new NotFoundException(String.format("Item with id [%s] not found", id));
        }
    }

    @Override
    public void addItem(AddItemDto itemDto, Optional<String> id, List<Item> state) {
        Item item = itemDtoToItem(itemDto.toItemDto());
        item.setId(id.orElse(UUID.randomUUID().toString()));
        if (findItem(item.getId(), state).isEmpty()) {
            state.add(item);
        } else {
            throw new DuplicateException(String.format("Item with the id [%s] already exist", item.getId()));
        }
    }

    @Override
    public void updateItem(String id, UpdateItemDto itemDto, List<Item> state) {
        Item item = itemDtoToItem(itemDto.toItemDto(id));
        Optional<Item> foundItem = findItem(id, state);
        if (foundItem.isPresent()) {
            state.set(state.indexOf(foundItem.get()), item);
        } else {
            throw new NotFoundException(String.format("Item with the id [%s] does not exist", id));
        }
    }

    @Override
    public void removeItem(String id, List<Item> state) {
        Optional<Item> foundItem = findItem(id, state);
        if (foundItem.isPresent()) {
            state.remove(foundItem.get());
        } else {
            throw new NotFoundException(String.format("Item with the id [%s] does not exist", id));
        }
    }

}
