package com.example.footmark.todo.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class CategoryMemberNotFoundException extends NotFoundGroupException {
    public CategoryMemberNotFoundException(String message){
        super(message);
    }

    public CategoryMemberNotFoundException(){
        this("해당 카테고리를 찾을 수 없습니다.");
    }
}
