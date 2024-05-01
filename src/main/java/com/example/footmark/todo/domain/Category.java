package com.example.footmark.todo.domain;

import com.example.footmark.member.domain.Member;
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String categoryName;

    @Column
    private String color;

    @Builder
    public Category(Long categoryId, Member member, String categoryName, String color) {
        this.categoryId = categoryId;
        this.member = member;
        this.categoryName = categoryName;
        this.color = color;
    }

}