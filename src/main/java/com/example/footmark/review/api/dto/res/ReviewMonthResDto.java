package com.example.footmark.review.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMonthResDto {
    String categoryName;

    Long reviewId;

    LocalDate reviewCreateAt;

    String reviewContent;

    String todayEmoji;

    public ReviewMonthResDto(Long reviewId, String categoryName, LocalDate reviewCreateAt, String reviewContent, String todayEmoji) {
        this.reviewId = reviewId;
        this.categoryName = categoryName;
        this.reviewCreateAt = reviewCreateAt;
        this.reviewContent = reviewContent;
        this.todayEmoji = todayEmoji;
    }
}
