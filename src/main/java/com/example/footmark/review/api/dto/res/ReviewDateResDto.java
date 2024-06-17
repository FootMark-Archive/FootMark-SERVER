package com.example.footmark.review.api.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDateResDto {
    Long categoryId;
    @Schema(description = "카테고리명", example = "운동")
    String categoryName;
    @Schema(description = "할일 내용", example = "캡스톤")
    List<String> todoContent;
    @Schema(description = "일기 내용", example = "알찬 하루")
    String reviewContent;

    public ReviewDateResDto(Long categoryId, String categoryName, List<String> todoContent, String reviewContent) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.todoContent = todoContent;
        this.reviewContent = reviewContent;
    }
}
