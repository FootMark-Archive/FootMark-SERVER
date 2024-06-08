package com.example.footmark.emoji.api.dto.res;

import com.example.footmark.emoji.domain.Emoji;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record EmojiResDto(
        Long emojiId,
        LocalDate createAt,
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
