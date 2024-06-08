package com.example.footmark.review.api.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReviewMonthReqDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull
        LocalDate startDate,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull
        LocalDate endDate
) {
}
