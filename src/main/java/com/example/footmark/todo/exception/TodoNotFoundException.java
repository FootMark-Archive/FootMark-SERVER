package com.example.footmark.todo.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class TodoNotFoundException extends NotFoundGroupException {
    public TodoNotFoundException(String message) {
        super(message);
    }

    public TodoNotFoundException() {
        this("해당 할 일을 찾을 수 없습니다.");
    }
}
