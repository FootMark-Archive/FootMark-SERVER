package com.example.footmark.emoji.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmojiReqDto(
        @NotNull
        @Schema(description = "오늘의 이모지", example = "2024-05-05")
        LocalDate createAt,
        @NotNull
        @Schema(description = "이모지", example = "\uD83E\uDD2C")
        String todayEmoji
) {
}
