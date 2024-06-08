package com.example.footmark.review.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class CategoryNotFoundException extends NotFoundGroupException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
        this("해당 카테고리를 찾을 수 없습니다.");
    }
}
