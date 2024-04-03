package com.example.footmark.global.jwt.api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//클라이언트에게 토큰을 보내기 위한 DTO
@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}