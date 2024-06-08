package com.example.footmark.emoji.api.dto.req;

import jakarta.validation.constraints.NotNull;

public record EmojiUpdateReqDto(
        @NotNull
        String todayEmoji
) {
}
