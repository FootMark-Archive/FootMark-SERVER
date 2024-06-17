package com.example.footmark.emoji.api.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record EmojiUpdateReqDto(
        @NotNull
        @Schema(description = "이모지", example = "\uD83E\uDD2C")
        String todayEmoji
) {
}
