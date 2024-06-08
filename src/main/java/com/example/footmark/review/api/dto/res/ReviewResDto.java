package com.example.footmark.review.api.dto.res;

import com.example.footmark.review.domain.Review;
import lombok.Builder;

@Builder
public record ReviewResDto(
        Long reviewId,
        Long memberId,
        Long categoryId,
        String content
) {
    public static ReviewResDto of(Review review) {
        return ReviewResDto.builder()
                .reviewId(review.getReviewId())
                .memberId(review.getMember().getMemberId())
                .categoryId(review.getCategory().getCategoryId())
                .content(review.getContent())
                .build();
    }
}
