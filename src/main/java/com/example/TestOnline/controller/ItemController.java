package com.example.TestOnline.controller;

import com.example.TestOnline.dto.ApiResponse;
import com.example.TestOnline.dto.ItemRequest;
import com.example.TestOnline.dto.ApiResponse;
import com.example.TestOnline.dto.ItemResponse;
import com.example.TestOnline.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklists/{checklistId}/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems(@PathVariable Long checklistId) {
        List<ItemResponse> items = itemService.getAllItemsByChecklistId(checklistId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long checklistId, @PathVariable Long itemId) {
        ItemResponse item = itemService.getItemById(checklistId, itemId);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@PathVariable Long checklistId, @Valid @RequestBody ItemRequest itemRequest) {
        ItemResponse item = itemService.createItem(checklistId, itemRequest);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> updateItem(@PathVariable Long checklistId, @PathVariable Long itemId, @Valid @RequestBody ItemRequest itemRequest) {
        ItemResponse item = itemService.updateItem(checklistId, itemId, itemRequest);
        return ResponseEntity.ok(item);
    }

    @PatchMapping("/{itemId}/status")
    public ResponseEntity<ItemResponse> updateItemStatus(@PathVariable Long checklistId, @PathVariable Long itemId, @RequestParam boolean completed) {
        ItemResponse item = itemService.updateItemStatus(checklistId, itemId, completed);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long checklistId, @PathVariable Long itemId) {
        itemService.deleteItem(checklistId, itemId);
        return ResponseEntity.ok(ApiResponse.success("Item deleted successfully"));
    }
}

