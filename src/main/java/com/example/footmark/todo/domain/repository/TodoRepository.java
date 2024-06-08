package com.example.footmark.todo.domain.repository;

import com.example.footmark.todo.domain.Category;
import com.example.footmark.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoCustomRepository {
    List<Todo> findByMemberMemberIdAndCreateAtBetween(Long memberId, LocalDate startOfDay, LocalDate endOfDay);

    void deleteByCategory(Category category);
}
