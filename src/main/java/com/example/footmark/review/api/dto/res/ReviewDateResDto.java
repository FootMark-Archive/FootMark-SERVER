package com.example.footmark.review.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDateResDto {
    Long categoryId;
    String categoryName;

    List<String> todoContent;

    String reviewContent;

    public ReviewDateResDto(Long categoryId, String categoryName, List<String> todoContent, String reviewContent) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.todoContent = todoContent;
        this.reviewContent = reviewContent;
    }
}
