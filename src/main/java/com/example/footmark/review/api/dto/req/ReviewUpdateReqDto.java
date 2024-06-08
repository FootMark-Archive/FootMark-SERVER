package com.example.footmark.review.api.dto.req;

import jakarta.validation.constraints.NotNull;

public record ReviewUpdateReqDto(
        @NotNull
        String content
) {
}
