package com.example.footmark.review.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class ReviewNotFoundException extends NotFoundGroupException {
    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException() {
        this("해당 일기를 찾을 수 없습니다.");
    }
}