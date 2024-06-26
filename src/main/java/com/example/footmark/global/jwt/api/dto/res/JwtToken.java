package com.example.footmark.global.jwt.api.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

//클라이언트에게 토큰을 보내기 위한 DTO
@Builder
public record JwtToken (
        String accessToken,
        String refreshToken
) {

}