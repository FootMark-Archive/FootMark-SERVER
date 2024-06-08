package com.example.footmark.review.domain;

import com.example.footmark.member.domain.Member;
import com.example.footmark.review.api.dto.req.ReviewUpdateReqDto;
import com.example.footmark.todo.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "일기 id", example = "1")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    @Schema(description = "일기 등록 날짜", example = "2024-05-02")
    private LocalDate createAt;

    @Column(nullable = false, length = 1000)
    @Schema(description = "일기 내용", example = "알찬 하루")
    private String content;



    @Builder
    public Review(Member member, Category category, LocalDate createAt, String content) {
        this.member = member;
        this.category = category;
        this.createAt = createAt;
        this.content = content;
    }

    public void update(ReviewUpdateReqDto reviewReqDto){
        this.content = reviewReqDto.content();
    }
}
