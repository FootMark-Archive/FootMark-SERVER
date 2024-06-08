package com.example.footmark.todo.domain;

import com.example.footmark.member.domain.Member;
import com.example.footmark.todo.api.dto.req.CategoryReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @Schema(description = "카테고리 id", example = "1")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @Schema(description = "카테고리명", example = "운동")
    private String categoryName;

    @Column(columnDefinition = "String default black")
    @Schema(description = "카테고리 색깔", example = "black")
    private String color;

    @Builder
    public Category(Long categoryId, Member member, String categoryName, String color) {
        this.categoryId = categoryId;
        this.member = member;
        this.categoryName = categoryName;
        this.color = color;
    }

    public void update(CategoryReqDto categoryReqDto){
        this.categoryName = categoryReqDto.categoryName();
        this.color = categoryReqDto.color();
    }

}