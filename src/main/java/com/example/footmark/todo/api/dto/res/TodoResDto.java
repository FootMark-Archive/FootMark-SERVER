package com.example.footmark.todo.api.dto.res;

import com.example.footmark.todo.domain.Todo;
import lombok.Builder;

@Builder
public record TodoResDto(
        Long todoId,
        Long categoryId,
        String content
) {
    public static TodoResDto of(Todo todo){
        return TodoResDto.builder()
                .todoId(todo.getTodoId())
                .categoryId(todo.getCategory().getCategoryId())
                .content(todo.getContent())
                .build();
    }
}
