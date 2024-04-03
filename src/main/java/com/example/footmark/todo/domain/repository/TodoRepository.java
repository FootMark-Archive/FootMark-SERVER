package com.example.footmark.todo.domain.repository;

import com.example.footmark.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
