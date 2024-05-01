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

    public TodoDateResDto(Long categoryId, String categoryName, List<String> content) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.content = content;
    }

}
