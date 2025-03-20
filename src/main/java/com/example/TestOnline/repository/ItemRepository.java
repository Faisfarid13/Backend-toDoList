package com.example.TestOnline.repository;

import com.example.TestOnline.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByChecklistId(Long checklistId);
    Optional<Item> findByIdAndChecklistId(Long id, Long checklistId);
}
