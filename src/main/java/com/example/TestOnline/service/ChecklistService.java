package com.example.TestOnline.service;

import com.example.TestOnline.entity.User;
import com.example.TestOnline.repository.UserRepository;
import com.example.TestOnline.dto.ChecklistRequest;
import com.example.TestOnline.dto.ChecklistResponse;
import com.example.TestOnline.entity.Checklist;
import com.example.TestOnline.entity.User;
import com.example.TestOnline.repository.ChecklistRepository;
import com.example.TestOnline.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChecklistService {
    private final ChecklistRepository checklistRepository;
    private final UserRepository userRepository;

    public ChecklistService(ChecklistRepository checklistRepository, UserRepository userRepository) {
        this.checklistRepository = checklistRepository;
        this.userRepository = userRepository;
    }

    public List<ChecklistResponse> getAllChecklists() {
        User currentUser = getCurrentUser();
        return checklistRepository.findByUserId(currentUser.getId()).stream()
                .map(this::mapToChecklistResponse)
                .collect(Collectors.toList());
    }

    public ChecklistResponse getChecklistById(Long id) {
        User currentUser = getCurrentUser();
        Checklist checklist = checklistRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));
        return mapToChecklistResponse(checklist);
    }

    @Transactional
    public ChecklistResponse createChecklist(ChecklistRequest checklistRequest) {
        User currentUser = getCurrentUser();

        Checklist checklist = new Checklist();
        checklist.setTitle(checklistRequest.getTitle());
        checklist.setDescription(checklistRequest.getDescription());
        checklist.setUser(currentUser);

        Checklist savedChecklist = checklistRepository.save(checklist);
        return mapToChecklistResponse(savedChecklist);
    }

    @Transactional
    public void deleteChecklist(Long id) {
        User currentUser = getCurrentUser();
        Checklist checklist = checklistRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Checklist not found"));
        checklistRepository.delete(checklist);
    }

    private ChecklistResponse mapToChecklistResponse(Checklist checklist) {
        int totalItems = checklist.getItems().size();
        int completedItems = (int) checklist.getItems().stream()
                .filter(item -> item.isCompleted())
                .count();

        return new ChecklistResponse(
                checklist.getId(),
                checklist.getTitle(),
                checklist.getDescription(),
                checklist.getCreatedAt(),
                totalItems,
                completedItems
        );
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}