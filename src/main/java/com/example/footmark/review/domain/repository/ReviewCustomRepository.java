package com.example.footmark.review.domain.repository;

import com.example.footmark.member.domain.Member;
import com.example.footmark.review.api.dto.req.ReviewDateReqDto;
import com.example.footmark.review.api.dto.req.ReviewMonthReqDto;
import com.example.footmark.review.api.dto.res.ReviewMonthResDto;
import com.example.footmark.review.api.dto.res.ReviewsResDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;


public interface ReviewCustomRepository {
    ReviewsResDto findAll(String createAt, Member member);

    Page<ReviewMonthResDto> findAllMonth(
            Member member, String categoryName, String startDate, String endDate, Pageable pageable);

}
