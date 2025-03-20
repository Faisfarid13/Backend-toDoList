package com.example.TestOnline.repository;

import com.example.TestOnline.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByUserId(Long userId);
    Optional<Checklist> findByIdAndUserId(Long id, Long userId);
}

