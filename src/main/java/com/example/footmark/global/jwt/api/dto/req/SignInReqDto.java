package com.example.footmark.global.jwt.api.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class SignInReqDto {
    private String username;
    private String password;
}
