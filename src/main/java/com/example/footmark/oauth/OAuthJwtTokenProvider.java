package com.example.footmark.oauth;

import com.example.footmark.global.jwt.api.dto.res.JwtToken;
import com.example.footmark.member.domain.Member;
import com.example.footmark.member.domain.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class OAuthJwtTokenProvider {
    private final Key key;

    @Value("${token.expire.time.access}")
    private String accessTokenExpireTime;

    @Value("${token.expire.time.refresh}")
    private String refreshTokenExpireTime;

    private final MemberRepository memberRepository;

    // application.yml에서 secret 값 가져와서 key에 저장
    public OAuthJwtTokenProvider(@Value("${jwt.secret}") String secretKey, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(String email) {
        String accessToken = generateAccessToken(email);
        String refreshToken = generateRefreshToken();

        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateAccessToken(String email) {
        Date date = new Date();
        Date accessExpiryDate = new Date(date.getTime() + Long.parseLong(accessTokenExpireTime));

        // 사용자 정보 조회
        Optional<Member> memberOptional = memberRepository.findByUsername(email);
        String authorities = "";

        //단일 권한이기 때문에 .getRole() 사용하였다.
        if (memberOptional.isPresent()) {
            authorities = memberOptional.get().getRole().toString();
        }

        return Jwts.builder()
                .setSubject(email)
                .claim("auth", authorities)
                .setIssuedAt(date)
                .setExpiration(accessExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken() {
        Date date = new Date();
        Date refreshExpiryDate = new Date(date.getTime() + Long.parseLong(refreshTokenExpireTime));

        return Jwts.builder()
                .setExpiration(refreshExpiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}
