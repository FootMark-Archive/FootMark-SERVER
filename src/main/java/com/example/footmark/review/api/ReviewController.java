package com.example.footmark.review.api;

import com.example.footmark.global.jwt.domain.CustomUserDetail;
import com.example.footmark.global.template.RspTemplate;
import com.example.footmark.review.api.dto.req.ReviewDateReqDto;
import com.example.footmark.review.api.dto.req.ReviewMonthReqDto;
import com.example.footmark.review.api.dto.req.ReviewReqDto;
import com.example.footmark.review.api.dto.req.ReviewUpdateReqDto;
import com.example.footmark.review.api.dto.res.ReviewMonthResDto;
import com.example.footmark.review.api.dto.res.ReviewResDto;
import com.example.footmark.review.api.dto.res.ReviewsResDto;
import com.example.footmark.review.application.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@Tag(name = "review", description = "Review Controller")
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "일기 등록", description = "일기 등록합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping
    public RspTemplate<ReviewResDto> createReview(@Valid @RequestBody ReviewReqDto reviewReqDto, @AuthenticationPrincipal CustomUserDetail member) {

        ReviewResDto reviewResDto = reviewService.createReview(reviewReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "일기 등록 성공", reviewResDto);
    }

    @Operation(summary = "당일 일기 조회", description = "당일 일기 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/daily")
    public RspTemplate<ReviewsResDto> getReviews(@Valid @RequestBody ReviewDateReqDto reviewDateReqDto, @AuthenticationPrincipal CustomUserDetail member) {
        ReviewsResDto reviewsResDto = reviewService.findAll(reviewDateReqDto, member.getMember());
        return new RspTemplate<>(HttpStatus.OK, "일기 목록 조회 성공", reviewsResDto);
    }

    @Operation(summary = "일기 수정", description = "일기 수정합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PutMapping("/{reviewId}")
    public RspTemplate<ReviewResDto> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewUpdateReqDto reviewReqDto,
                                              @AuthenticationPrincipal CustomUserDetail member) {

        ReviewResDto reviewResDto = reviewService.updateReview(reviewId, reviewReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "일기 수정 성공", reviewResDto);
    }

    @Operation(summary = "일기 삭제", description = "일기 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/{reviewId}")
    public RspTemplate<Void> deleteReview(@PathVariable Long reviewId,
                                        @AuthenticationPrincipal CustomUserDetail member){

        reviewService.deleteReview(reviewId, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "일기 삭제 성공");
    }

    @Operation(summary = "한달 일기 조회", description = "한달 일기 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/monthly")
    public RspTemplate<Page<ReviewMonthResDto>> getReviews(
            @Valid @RequestBody ReviewMonthReqDto reviewMonthReqDto,
            @AuthenticationPrincipal CustomUserDetail member,
            @RequestParam(required = false) String categoryName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        Page<ReviewMonthResDto> reviewMonthResDto = reviewService.findAllMonth(reviewMonthReqDto, member.getMember(), categoryName, pageable);
        return new RspTemplate<>(HttpStatus.OK, "일기 목록 조회 성공", reviewMonthResDto);
    }

}
