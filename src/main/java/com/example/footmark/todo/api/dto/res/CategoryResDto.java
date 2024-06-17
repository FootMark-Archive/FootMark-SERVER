package com.example.footmark.todo.api.dto.res;

import com.example.footmark.todo.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryResDto(
        Long categoryId,
        @Schema(description = "카테고리명", example = "운동")
        String categoryName,
        @Schema(description = "카테고리 색깔", example = "black")
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
