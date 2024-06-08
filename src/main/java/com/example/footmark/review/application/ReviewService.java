package com.example.footmark.review.application;

import com.example.footmark.member.domain.Member;
import com.example.footmark.review.api.dto.req.ReviewDateReqDto;
import com.example.footmark.review.api.dto.req.ReviewMonthReqDto;
import com.example.footmark.review.api.dto.req.ReviewReqDto;
import com.example.footmark.review.api.dto.req.ReviewUpdateReqDto;
import com.example.footmark.review.api.dto.res.ReviewMonthResDto;
import com.example.footmark.review.api.dto.res.ReviewResDto;
import com.example.footmark.review.api.dto.res.ReviewsResDto;
import com.example.footmark.review.domain.Review;
import com.example.footmark.review.domain.repository.ReviewRepository;
import com.example.footmark.review.exception.CategoryNotFoundException;
import com.example.footmark.review.exception.ReviewMemberMismatchException;
import com.example.footmark.review.exception.ReviewNotFoundException;
import com.example.footmark.review.exception.ReviewOrMemberNullException;
import com.example.footmark.todo.domain.Category;
import com.example.footmark.todo.domain.repository.CategoryRepository;
import com.example.footmark.todo.exception.CategoryMemberMismatchException;
import com.example.footmark.todo.exception.CategoryOrMemberNullException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResDto createReview(ReviewReqDto reviewReqDto, Member member){
        Category category = categoryRepository.findById(reviewReqDto.categoryId()).orElseThrow(CategoryNotFoundException::new);

        validateCategoryMemberMatch(category, member);

        Review review = builderReview(reviewReqDto, member, category);

        Review saveReview = reviewRepository.save(review);

        return ReviewResDto.of(saveReview);
    }

    private Review builderReview(ReviewReqDto reviewReqDto, Member member, Category category){
        return Review.builder()
                .member(member)
                .category(category)
                .createAt(reviewReqDto.createAt())
                .content(reviewReqDto.content())
                .build();
    }

    private void validateCategoryMemberMatch(Category category, Member member){
        if(category == null || member == null) {
            throw new CategoryOrMemberNullException();
        }

        if(!category.getMember().getMemberId().equals(member.getMemberId())) {
            throw new CategoryMemberMismatchException();
        }
    }

    public ReviewsResDto findAll(ReviewDateReqDto reviewDateReqDto, Member member) {
        return reviewRepository.findAll(reviewDateReqDto, member);
    }

    @Transactional
    public ReviewResDto updateReview(Long reviewId, ReviewUpdateReqDto reviewReqDto, Member member){
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        validateReviewMemberMatch(review, member);

        review.update(reviewReqDto);
        return ReviewResDto.of(review);
    }

    private void validateReviewMemberMatch(Review review, Member member){
        if(review == null || member == null) {
            throw new ReviewOrMemberNullException();
        }

        if(!review.getMember().getMemberId().equals(member.getMemberId())) {
            throw new ReviewMemberMismatchException();
        }
    }

    @Transactional
    public void deleteReview(Long reviewId, Member member){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        validateReviewMemberMatch(review, member);

        reviewRepository.delete(review);
    }

    public Page<ReviewMonthResDto> findAllMonth(ReviewMonthReqDto reviewMonthReqDto, Member member, String categoryName, Pageable pageable) {
        return reviewRepository.findAllMonth(member, categoryName, reviewMonthReqDto, pageable);
    }

}
