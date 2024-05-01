package com.example.footmark.todo.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class CategoryOrMemberNullException extends NotFoundGroupException {

    public CategoryOrMemberNullException(String message) {
        super(message);
    }

    public CategoryOrMemberNullException() {
        this("카테고리와 멤버는 null일 수 없습니다.");
    }
}
