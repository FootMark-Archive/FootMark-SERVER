package com.example.footmark.review.exception;

import com.example.footmark.global.error.exception.InvalidGroupException;

public class ReviewMemberMismatchException extends InvalidGroupException {

    public ReviewMemberMismatchException(String message){
        super(message);
    }

    public ReviewMemberMismatchException(){
        this("카테고리의 멤버와 요청 멤버가 일치하지 않습니다.");
    }

}