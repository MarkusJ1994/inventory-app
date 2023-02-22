package com.example.inventory.controller;

import com.example.inventory.aggregator.Step;
import com.example.inventory.domain.items.data.Item;
import com.example.inventory.domain.items.dto.AddItemDto;
import com.example.inventory.domain.items.dto.ItemDto;
import com.example.inventory.domain.items.dto.UpdateItemDto;
import com.example.inventory.domain.items.events.AddItemCommand;
import com.example.inventory.domain.items.events.DeleteItemCommand;
import com.example.inventory.domain.items.events.ItemEventQueue;
import com.example.inventory.domain.items.events.UpdateItemCommand;
import com.example.inventory.domain.items.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final ItemEventQueue eventQueue;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        //last step in chain is current state
        Step<List<Item>> lastStep = eventQueue.fold()
                .stream()
                .reduce((f, s) -> s).orElse(null);

        return ResponseEntity.ok(
                lastStep != null ?
                        lastStep.state().stream().map(InventoryService::itemToItemDto).toList()
                        : new ArrayList<>());
    }

    @GetMapping("/aggregation")
    public ResponseEntity<List<Step<List<ItemDto>>>> getAggregation() {
        return ResponseEntity.ok(
                eventQueue.fold()
                        .stream()
                        .map(step -> {
                            List<ItemDto> state = step.state()
                                    .stream()
                                    .map(InventoryService::itemToItemDto)
                                    .toList();
                            return new Step<>(step.event(), step.result(), state);
                        })
                        .toList());
    }

    @PostMapping
    public ResponseEntity addItem(@RequestBody AddItemDto itemDto) {
        eventQueue.add(new AddItemCommand(itemDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateItem(@PathVariable String id, @RequestBody UpdateItemDto itemDto) {
        eventQueue.add(new UpdateItemCommand(id, itemDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable String id) {
        eventQueue.add(new DeleteItemCommand(id));
        return ResponseEntity.ok().build();
    }

}
