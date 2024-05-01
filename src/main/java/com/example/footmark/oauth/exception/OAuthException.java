package com.example.footmark.oauth.exception;

import com.example.footmark.global.error.exception.AuthGroupException;

public class OAuthException extends AuthGroupException {
    public OAuthException(String message) {
        super(message);
    }

    public OAuthException() {
        this("id 토큰을 읽을 수 없습니다.");
    }
}
