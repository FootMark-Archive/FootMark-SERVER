package com.example.footmark.emoji.exception;

import com.example.footmark.global.error.exception.NotFoundGroupException;

public class EmojiNotFoundException extends NotFoundGroupException {
    public EmojiNotFoundException(String message) {
        super(message);
    }

    public EmojiNotFoundException() {
        this("이모지가 존재하지 않습니다.");
    }
}
