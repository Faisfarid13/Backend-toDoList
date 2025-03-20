package com.example.TestOnline.controller;

import com.example.TestOnline.dto.ApiResponse;
import com.example.TestOnline.dto.ChecklistRequest;
import com.example.TestOnline.dto.ApiResponse;
import com.example.TestOnline.dto.ChecklistResponse;
import com.example.TestOnline.service.ChecklistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {
    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @GetMapping
    public ResponseEntity<List<ChecklistResponse>> getAllChecklists() {
        List<ChecklistResponse> checklists = checklistService.getAllChecklists();
        return ResponseEntity.ok(checklists);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistResponse> getChecklistById(@PathVariable Long id) {
        ChecklistResponse checklist = checklistService.getChecklistById(id);
        return ResponseEntity.ok(checklist);
    }

    @PostMapping
    public ResponseEntity<ChecklistResponse> createChecklist(@Valid @RequestBody ChecklistRequest checklistRequest) {
        ChecklistResponse checklist = checklistService.createChecklist(checklistRequest);
        return ResponseEntity.ok(checklist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteChecklist(@PathVariable Long id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.ok(ApiResponse.success("Checklist deleted successfully"));
    }
}