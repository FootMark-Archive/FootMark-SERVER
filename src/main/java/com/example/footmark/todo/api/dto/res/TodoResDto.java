package com.example.footmark.todo.api.dto.res;

import com.example.footmark.todo.domain.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record TodoResDto(
        Long todoId,
        Long categoryId,
        @Schema(description = "할일 내용", example = "캡스톤")
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
