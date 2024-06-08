package com.example.footmark.emoji.exception;

import com.example.footmark.global.error.exception.InvalidGroupException;

public class EmojiAlreadyExistsForDateException extends InvalidGroupException {
    public EmojiAlreadyExistsForDateException(String message) {
        super(message);
    }

    public EmojiAlreadyExistsForDateException() {
        this("이 멤버는 이미 해당 날짜에 이모지를 등록했습니다.");
    }
}
