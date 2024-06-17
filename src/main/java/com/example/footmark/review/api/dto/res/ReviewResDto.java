package com.example.footmark.review.api.dto.res;

import com.example.footmark.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReviewResDto(
        Long reviewId,
        Long memberId,
        Long categoryId,
        @Schema(description = "일기 내용", example = "알찬 하루")
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
