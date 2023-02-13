package com.example.inventory.controller;

import com.example.inventory.domain.EventDriver;
import com.example.inventory.domain.dto.AddItemDto;
import com.example.inventory.domain.dto.ItemDto;
import com.example.inventory.domain.dto.UpdateItemDto;
import com.example.inventory.domain.events.AddItemCommand;
import com.example.inventory.domain.events.DeleteItemCommand;
import com.example.inventory.domain.events.UpdateItemCommand;
import com.example.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final EventDriver eventDriver;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable String id) {
        return ResponseEntity.ok(inventoryService.findItemById(id));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItems() {
        return ResponseEntity.ok(inventoryService.findItems());
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody AddItemDto itemDto) {
        return ResponseEntity.ok(eventDriver.applyCommand(new AddItemCommand(itemDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable String id, @RequestBody UpdateItemDto itemDto) {
        return ResponseEntity.ok(eventDriver.applyCommand(new UpdateItemCommand(id, itemDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        eventDriver.applyCommand(new DeleteItemCommand(id));
        return ResponseEntity.ok().build();
    }

}
