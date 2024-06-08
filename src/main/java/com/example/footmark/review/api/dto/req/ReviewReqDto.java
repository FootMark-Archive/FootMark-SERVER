package com.example.footmark.review.api.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReviewReqDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull
        LocalDate createAt,
        @NotNull
        Long categoryId,
        @NotNull
        String content
) {
}
