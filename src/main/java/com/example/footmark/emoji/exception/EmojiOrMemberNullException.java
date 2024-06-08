package com.example.footmark.emoji.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class EmojiOrMemberNullException extends NotFoundGroupException {
    public EmojiOrMemberNullException(String message) {
        super(message);
    }

    public EmojiOrMemberNullException() {
        this("이모지와 멤버는 null일 수 없습니다.");
    }
}
