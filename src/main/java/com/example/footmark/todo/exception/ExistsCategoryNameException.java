package com.example.footmark.todo.exception;

import com.example.footmark.global.error.exception.InvalidGroupException;

public class ExistsCategoryNameException extends InvalidGroupException {
    public ExistsCategoryNameException(String message) {
        super(message);
    }

    public ExistsCategoryNameException() {
        this("이미 해당 멤버에 동일한 카테고리가 존재합니다.");
    }
}
