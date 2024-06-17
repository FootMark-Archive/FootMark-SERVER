package com.example.footmark.todo.api.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoryReqDto(
        @NotBlank
        @Schema(description = "카테고리명", example = "운동")
        String categoryName,
        @Schema(description = "카테고리 색깔", example = "black")
        String color
) {
}
