package com.example.footmark.todo.exception;

import com.example.footmark.global.error.exception.InvalidGroupException;

public class TodoMemberMismatchException extends InvalidGroupException {
    public TodoMemberMismatchException(String message){
        super(message);
    }

    public TodoMemberMismatchException(){
        this("할일의 멤버와 요청 멤버가 일치하지 않습니다.");
    }

}
