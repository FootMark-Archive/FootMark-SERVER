package com.example.footmark.emoji.exception;

import com.example.footmark.global.error.exception.InvalidGroupException;

public class EmojiMemberMismatchException extends InvalidGroupException {
    public EmojiMemberMismatchException(String message){
        super(message);
    }

    public EmojiMemberMismatchException(){
        this("이모지의 멤버와 요청 멤버가 일치하지 않습니다.");
    }
}
