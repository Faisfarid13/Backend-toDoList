package com.example.TestOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private int totalItems;
    private int completedItems;
}
