package com.example.TestOnline.service;

import com.example.TestOnline.entity.User;
import com.example.TestOnline.repository.UserRepository;
import com.example.TestOnline.dto.ItemRequest;
import com.example.TestOnline.dto.ItemResponse;
import com.example.TestOnline.entity.Checklist;
import com.example.TestOnline.entity.Item;
import com.example.TestOnline.entity.User;
import com.example.TestOnline.repository.ChecklistRepository;
import com.example.TestOnline.repository.ItemRepository;
import com.example.TestOnline.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ChecklistRepository checklistRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, ChecklistRepository checklistRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.checklistRepository = checklistRepository;
        this.userRepository = userRepository;
    }

    public List<ItemResponse> getAllItemsByChecklistId(Long checklistId) {
        User currentUser = getCurrentUser();
        Checklist checklist = checklistRepository.findByIdAndUserId(checklistId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));

        return itemRepository.findByChecklistId(checklistId).stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long checklistId, Long itemId) {
        User currentUser = getCurrentUser();
        checklistRepository.findByIdAndUserId(checklistId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));

        Item item = itemRepository.findByIdAndChecklistId(itemId, checklistId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        return mapToItemResponse(item);
    }

    @Transactional
    public ItemResponse createItem(Long checklistId, ItemRequest itemRequest) {
        User currentUser = getCurrentUser();
        Checklist checklist = checklistRepository.findByIdAndUserId(checklistId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));

        Item item = new Item();
        item.setName(itemRequest.getName());
        item.setCompleted(itemRequest.isCompleted());
        item.setChecklist(checklist);

        Item savedItem = itemRepository.save(item);
        return mapToItemResponse(savedItem);
    }

    @Transactional
    public ItemResponse updateItem(Long checklistId, Long itemId, ItemRequest itemRequest) {
        User currentUser = getCurrentUser();
        checklistRepository.findByIdAndUserId(checklistId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));

        Item item = itemRepository.findByIdAndChecklistId(itemId, checklistId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setName(itemRequest.getName());
        item.setCompleted(itemRequest.isCompleted());

        Item updatedItem = itemRepository.save(item);
        return mapToItemResponse(updatedItem);
    }

    @Transactional
    public ItemResponse updateItemStatus(Long checklistId, Long itemId, boolean completed) {
        User currentUser = getCurrentUser();
        checklistRepository.findByIdAndUserId(checklistId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));

        Item item = itemRepository.findByIdAndChecklistId(itemId, checklistId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setCompleted(completed);

        Item updatedItem = itemRepository.save(item);
        return mapToItemResponse(updatedItem);
    }

    @Transactional
    public void deleteItem(Long checklistId, Long itemId) {
        User currentUser = getCurrentUser();
        checklistRepository.findByIdAndUserId(checklistId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));

        Item item = itemRepository.findByIdAndChecklistId(itemId, checklistId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        itemRepository.delete(item);
    }

    private ItemResponse mapToItemResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.isCompleted(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
