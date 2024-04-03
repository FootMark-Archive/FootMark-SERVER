package com.example.footmark.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}
