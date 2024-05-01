package com.example.footmark.todo.api.dto.req;


import jakarta.validation.constraints.NotBlank;

public record CategoryReqDto(
        @NotBlank
        String categoryName,
        String color
) {
}
