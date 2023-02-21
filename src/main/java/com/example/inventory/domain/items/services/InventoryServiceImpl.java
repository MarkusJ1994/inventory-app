package com.example.inventory.domain.items.services;

import com.example.inventory.aggregator.Result;
import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;
import com.example.inventory.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.inventory.domain.items.services.InventoryService.itemDtoToItem;
import static com.example.inventory.domain.items.services.InventoryService.itemToItemDto;

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
    public Step<List<Item>> addItem(AddItemDto itemDto, Optional<String> id, Optional<Step<List<Item>>> previousStep) {
        Item item = itemDtoToItem(itemDto.toItemDto());
        //Id can not be auto generated, since replaying a log would then yield non-deterministic results
        item.setId(id.orElse(itemDto.getName()));

        List<Item> currentState = previousStep.isPresent() ? previousStep.get().state() : new ArrayList<>();
        if (findItem(item.getId(), currentState).isEmpty()) {
            List<Item> updatedItems = new ArrayList<>(currentState);
            updatedItems.add(item);
            return new Step<>(updatedItems, Result.ok());
        } else {
            return new Step<>(currentState, Result.failure(String.format("Item with the id [%s] already exist", item.getId())));
        }
    }

    @Override
    public Step<List<Item>> updateItem(String id, UpdateItemDto itemDto, Optional<Step<List<Item>>> previousStep) {
        Item item = itemDtoToItem(itemDto.toItemDto(id));

        List<Item> currentState = previousStep.isPresent() ? previousStep.get().state() : new ArrayList<>();
        Optional<Item> foundItem = findItem(id, currentState);
        if (foundItem.isPresent()) {
            List<Item> updatedItems = new ArrayList<>(currentState);
            updatedItems.set(currentState.indexOf(foundItem.get()), item);
            return new Step<>(updatedItems, Result.ok());
        } else {
            return new Step<>(currentState, Result.failure(String.format("Item with the id [%s] does not exist", id)));
        }
    }

    @Override
    public Step<List<Item>> removeItem(String id, Optional<Step<List<Item>>> previousStep) {
        List<Item> currentState = previousStep.isPresent() ? previousStep.get().state() : new ArrayList<>();
        Optional<Item> foundItem = findItem(id, currentState);
        if (foundItem.isPresent()) {
            List<Item> updatedItems = new ArrayList<>(currentState);
            updatedItems.remove(foundItem.get());
            return new Step<>(updatedItems, Result.ok());
        } else {
            return new Step<>(currentState, Result.failure(String.format("Item with the id [%s] does not exist", id)));
        }
    }

}
