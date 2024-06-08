package com.example.footmark.review.api.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class ReviewsResDto {
    List<ReviewDateResDto> reviewDateResDtos;
    String todayEmoji;

    public ReviewsResDto(List<ReviewDateResDto> reviewDateResDtos, String todayEmoji) {
        this.reviewDateResDtos = reviewDateResDtos;
        this.todayEmoji = todayEmoji;
    }
}
