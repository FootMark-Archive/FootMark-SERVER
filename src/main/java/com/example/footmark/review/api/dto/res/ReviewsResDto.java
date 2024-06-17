package com.example.footmark.review.api.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewsResDto {
    List<ReviewDateResDto> reviewDateResDtos;
    @Schema(description = "이모지", example = "\uD83E\uDD2C")
    String todayEmoji;

    public ReviewsResDto(List<ReviewDateResDto> reviewDateResDtos, String todayEmoji) {
        this.reviewDateResDtos = reviewDateResDtos;
        this.todayEmoji = todayEmoji;
    }
}
