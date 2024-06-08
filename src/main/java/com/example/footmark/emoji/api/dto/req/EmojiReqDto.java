package com.example.footmark.emoji.api.dto.req;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmojiReqDto(
        @NotNull
        LocalDate createAt,
        @NotNull
        String todayEmoji
) {
}
