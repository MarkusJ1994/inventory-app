package com.example.inventory.domain.items.services;

import com.example.inventory.aggregator.Result;
import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.events.DomainEvent;
import com.example.inventory.domain.exceptions.NotFoundException;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;
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
    public InventoryMutationResult addItem(AddItemDto itemDto, Optional<String> id) {
        return addItem(itemDto, id, null);
    }

    @Override
    public InventoryMutationResult addItem(AddItemDto itemDto, Optional<String> id, Step<List<Item>> previousStep) {
        Item item = itemDtoToItem(itemDto.toItemDto());
        //Id can not be auto generated, since replaying a log would then yield non-deterministic results
        item.setId(id.orElse(itemDto.getName()));

        List<Item> currentState = previousStep != null ? previousStep.state() : new ArrayList<>();
        if (findItem(item.getId(), currentState).isEmpty()) {
            List<Item> updatedItems = new ArrayList<>(currentState);
            updatedItems.add(item);
            return new InventoryMutationResult(updatedItems, Result.ok());
        } else {
            return new InventoryMutationResult(currentState, Result.failure(String.format("Item with the id [%s] already exist", item.getId())));
        }
    }

    @Override
    public InventoryMutationResult updateItem(String id, UpdateItemDto itemDto) {
        return updateItem(id, itemDto, null);
    }

    @Override
    public InventoryMutationResult updateItem(String id, UpdateItemDto itemDto, Step<List<Item>> previousStep) {
        Item item = itemDtoToItem(itemDto.toItemDto(id));

        List<Item> currentState = previousStep != null ? previousStep.state() : new ArrayList<>();
        Optional<Item> foundItem = findItem(id, currentState);
        if (foundItem.isPresent()) {
            List<Item> updatedItems = new ArrayList<>(currentState);
            updatedItems.set(currentState.indexOf(foundItem.get()), item);
            return new InventoryMutationResult(updatedItems, Result.ok());
        } else {
            return new InventoryMutationResult(currentState, Result.failure(String.format("Item with the id [%s] does not exist", id)));
        }
    }

    @Override
    public InventoryMutationResult removeItem(String id) {
        return removeItem(id, null);
    }


    @Override
    public InventoryMutationResult removeItem(String id, Step<List<Item>> previousStep) {
        List<Item> currentState = previousStep != null ? previousStep.state() : new ArrayList<>();
        Optional<Item> foundItem = findItem(id, currentState);
        if (foundItem.isPresent()) {
            List<Item> updatedItems = new ArrayList<>(currentState);
            updatedItems.remove(foundItem.get());
            return new InventoryMutationResult(updatedItems, Result.ok());
        } else {
            return new InventoryMutationResult(currentState, Result.failure(String.format("Item with the id [%s] does not exist", id)));
        }
    }

    public record InventoryMutationResult(List<Item> state, Result result) {

        public Step<List<Item>> toStep(DomainEvent event) {
            return new Step<>(event, result, state);
        }

    }

}
