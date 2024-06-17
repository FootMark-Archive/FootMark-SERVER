package com.example.footmark.emoji.api.dto.res;

import com.example.footmark.emoji.domain.Emoji;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record EmojiResDto(
        Long emojiId,
        @Schema(description = "오늘의 이모지", example = "2024-05-05")
        LocalDate createAt,
        @Schema(description = "이모지", example = "\uD83E\uDD2C")
        String todayEmoji
) {
    public static EmojiResDto of(Emoji emoji) {
        return EmojiResDto.builder()
                .emojiId(emoji.getEmojiId())
                .createAt(emoji.getCreateAt())
                .todayEmoji(emoji.getTodayEmoji())
                .build();
    }
}
