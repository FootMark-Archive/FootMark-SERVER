package com.example.footmark.todo.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoDateResDto {

    Long categoryId;
    String categoryName;
    List<String> content;
    List<Boolean> isCompleted;

    List<Long> todoId;

    public TodoDateResDto(Long categoryId, String categoryName, List<String> content, List<Boolean> isCompleted,List<Long> todoId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.content = content;
        this.isCompleted = isCompleted;
        this.todoId = todoId;
    }

}
