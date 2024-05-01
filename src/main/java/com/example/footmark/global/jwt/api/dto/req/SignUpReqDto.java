package com.example.footmark.global.jwt.api.dto.req;

import jakarta.validation.constraints.NotBlank;


public record SignUpReqDto(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String nickname,
        String picture
) {}
