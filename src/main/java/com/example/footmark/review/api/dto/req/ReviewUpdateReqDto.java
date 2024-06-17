package com.example.footmark.review.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ReviewUpdateReqDto(
        @NotNull
        @Schema(description = "일기 내용", example = "알찬 하루")
        String content
) {
}
