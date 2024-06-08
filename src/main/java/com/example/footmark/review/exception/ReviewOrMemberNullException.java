package com.example.footmark.review.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class ReviewOrMemberNullException extends NotFoundGroupException {
    public ReviewOrMemberNullException(String message) {
        super(message);
    }

    public ReviewOrMemberNullException() {
        this("카테고리와 멤버는 null일 수 없습니다.");
    }
}