package com.example.footmark.review.domain.repository;

import com.example.footmark.member.domain.Member;
import com.example.footmark.review.api.dto.req.ReviewDateReqDto;
import com.example.footmark.review.api.dto.req.ReviewMonthReqDto;
import com.example.footmark.review.api.dto.res.ReviewDateResDto;
import com.example.footmark.review.api.dto.res.ReviewMonthResDto;
import com.example.footmark.review.api.dto.res.ReviewsResDto;
import com.example.footmark.todo.domain.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.footmark.emoji.domain.QEmoji.emoji;
import static com.example.footmark.review.domain.QReview.review;
import static com.example.footmark.todo.domain.QCategory.category;
import static com.example.footmark.todo.domain.QTodo.todo;

@RequiredArgsConstructor
@Repository
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ReviewsResDto findAll(ReviewDateReqDto reviewDateReqDto, Member member) {
        LocalDate date = reviewDateReqDto.createAt(); // 요청받은 날짜

        List<Category> categories = queryFactory
                .selectFrom(category)
                .where(category.member.eq(member))
                .fetch();

        List<ReviewDateResDto> result = new ArrayList<>();

        categories.forEach(cat -> {
            List<String> todoContents = queryFactory
                    .select(todo.content)
                    .from(todo)
                    .where(todo.category.eq(cat), todo.member.eq(member), todo.createAt.eq(date))
                    .fetch();

            String reviewContent = queryFactory
                    .select(review.content)
                    .from(review)
                    .where(review.category.eq(cat), review.member.eq(member), review.createAt.eq(date))
                    .fetchOne();

            if (!todoContents.isEmpty() || reviewContent != null) {
                result.add(new ReviewDateResDto(cat.getCategoryId(), cat.getCategoryName(), todoContents, reviewContent));
            }
        });

        String todayEmoji = queryFactory
                .select(emoji.todayEmoji)
                .from(emoji)
                .where(emoji.member.eq(member), emoji.createAt.eq(date))
                .fetchOne();

        return new ReviewsResDto(result, todayEmoji);
    }

    @Override
    public Page<ReviewMonthResDto> findAllMonth(
            Member member, String categoryName, ReviewMonthReqDto reviewMonthReqDto, Pageable pageable) {
        LocalDate startDate = reviewMonthReqDto.startDate();
        LocalDate endDate = reviewMonthReqDto.endDate();

        // categoryName이 null일 때와 아닐 때를 구분하여 조건을 생성
        BooleanExpression categoryCondition = categoryName != null ? review.category.categoryName.eq(categoryName) : null;
        BooleanExpression dateCondition = review.createAt.between(startDate, endDate);
        BooleanExpression memberCondition = review.member.eq(member);

        List<ReviewMonthResDto> content = queryFactory
                .select(Projections.constructor(ReviewMonthResDto.class,
                        review.reviewId,
                        review.category.categoryName,
                        review.createAt,
                        review.content,
                        emoji.todayEmoji))
                .from(review)
                .leftJoin(emoji)
                .on(review.createAt.eq(emoji.createAt).and(review.member.eq(emoji.member)))
                .where(memberCondition
                        .and(categoryCondition != null ? categoryCondition : Expressions.TRUE) // categoryName이 null이 아니면 해당 조건 추가, null이면 TRUE (항상 참)
                        .and(dateCondition))
                .orderBy(review.createAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(review)
                .leftJoin(emoji).on(review.createAt.eq(emoji.createAt).and(review.member.eq(emoji.member)))
                .where(memberCondition
                        .and(categoryCondition != null ? categoryCondition : Expressions.TRUE)
                        .and(dateCondition))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

}
