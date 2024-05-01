package com.example.footmark.todo.exception;

import com.example.footmark.global.error.exception.InvalidGroupException;

public class CategoryMemberMismatchException extends InvalidGroupException {
    public CategoryMemberMismatchException(String message){
        super(message);
    }

    public CategoryMemberMismatchException(){
        this("카테고리의 멤버와 요청 멤버가 일치하지 않습니다.");
    }

}
