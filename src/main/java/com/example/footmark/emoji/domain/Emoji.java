package com.example.footmark.emoji.domain;

import com.example.footmark.emoji.api.dto.req.EmojiUpdateReqDto;
import com.example.footmark.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "이모지 id", example = "1")
    private Long emojiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    @Schema(description = "오늘의 이모지", example = "2024-05-05")
    private LocalDate createAt;

    @Column
    @Schema(description = "이모지", example = "\uD83E\uDD2C")
    private String todayEmoji;

    @Builder
    public Emoji(Long emojiId, Member member, LocalDate createAt, String todayEmoji){
        this.emojiId = emojiId;
        this.member = member;
        this.createAt = createAt;
        this.todayEmoji = todayEmoji;
    }

    public void update(EmojiUpdateReqDto emojiReqDto){
        this.todayEmoji = emojiReqDto.todayEmoji();
    }
}
