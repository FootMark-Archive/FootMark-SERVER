package com.example.footmark.review.api.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMonthResDto {
    String categoryName;

    Long reviewId;
    @Schema(description = "일기 등록 날짜", example = "2024-05-02")
    LocalDate reviewCreateAt;
    @Schema(description = "일기 내용", example = "알찬 하루")
    String reviewContent;
    @Schema(description = "이모지", example = "\uD83E\uDD2C")
    String todayEmoji;

    public ReviewMonthResDto(Long reviewId, String categoryName, LocalDate reviewCreateAt, String reviewContent, String todayEmoji) {
        this.reviewId = reviewId;
        this.categoryName = categoryName;
        this.reviewCreateAt = reviewCreateAt;
        this.reviewContent = reviewContent;
        this.todayEmoji = todayEmoji;
    }
}
