package com.example.footmark.todo.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TodoReqDto(
        @NotBlank
        @Schema(description = "할일 내용", example = "캡스톤")
        String content
) {
}
