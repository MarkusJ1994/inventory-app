package com.example.inventory.controller;

import com.example.inventory.data.Item;
import com.example.inventory.domain.State;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;
import com.example.inventory.domain.events.AddItemCommand;
import com.example.inventory.domain.events.DeleteItemCommand;
import com.example.inventory.domain.events.UpdateItemCommand;
import com.example.inventory.domain.items.ItemEventQueue;
import com.example.inventory.services.InventoryService;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<ItemDto>> getItems(@Nullable @RequestBody State<List<ItemDto>> state) {
        List<Item> parsedStateList = state != null ? state.state().stream().map(InventoryService::itemDtoToItem).toList() : new ArrayList<>();
        return ResponseEntity.ok(
                eventQueue.fold(new State<>(parsedStateList, UUID.randomUUID()))
                        .stream()
                        .map(InventoryService::itemToItemDto)
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
