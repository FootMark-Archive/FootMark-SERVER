package com.example.footmark.todo.api.dto.res;

import com.example.footmark.todo.domain.Category;
import lombok.Builder;

@Builder
public record CategoryResDto(
        Long categoryId,
        String categoryName,
        String color
) {

    public static CategoryResDto of(Category category){
        return CategoryResDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .color(category.getColor())
                .build();
    }

}
