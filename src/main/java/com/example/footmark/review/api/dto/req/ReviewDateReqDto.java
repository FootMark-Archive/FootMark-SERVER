package com.example.footmark.review.api.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReviewDateReqDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "일기 등록 날짜", example = "2024-05-02")
        @NotNull
        LocalDate createAt
) {
}
