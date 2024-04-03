package com.example.footmark.global.jwt.application;

import com.example.footmark.global.jwt.JwtTokenProvider;
import com.example.footmark.global.jwt.api.dto.req.RefreshTokenReqDto;
import com.example.footmark.global.jwt.api.dto.res.JwtToken;
import com.example.footmark.global.jwt.domain.Token;
import com.example.footmark.global.jwt.domain.repository.TokenRepository;
import com.example.footmark.global.jwt.api.dto.req.SignUpReqDto;
import com.example.footmark.global.jwt.api.dto.res.MemberTokenResDto;
import com.example.footmark.global.jwt.exception.IllegalArgumentException;
import com.example.footmark.global.jwt.exception.InvalidTokenException;
import com.example.footmark.member.domain.Member;
import com.example.footmark.member.domain.repository.MemberRepository;
import com.example.footmark.global.jwt.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@Slf4j
public class TokenService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    public TokenService(MemberRepository memberRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.memberRepository = memberRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        Member member = memberRepository.findByUsername(username).orElseThrow(MemberNotFoundException::new);

        // 토큰 만료 시간 설정
        Date refreshTokenExpiryDate = new Date(System.currentTimeMillis() + 604800000);

        // 멤버에 대한 기존 토큰 조회
        Optional<Token> existingToken = tokenRepository.findByMember(member);

        if (existingToken.isPresent()) {
            // 기존 토큰이 있으면 업데이트
            Token token = existingToken.get();
            token.updateToken(jwtToken.getRefreshToken(), refreshTokenExpiryDate);
            tokenRepository.save(token);
        } else {
            // 기존 토큰이 없으면 새로 생성
            Token tokenEntity = new Token(jwtToken.getRefreshToken(), member, refreshTokenExpiryDate);
            tokenRepository.save(tokenEntity);
        }

        return jwtToken;
    }

    @Transactional
    public MemberTokenResDto signUp(SignUpReqDto signUpReqDto) {
        if (memberRepository.existsByUsername(signUpReqDto.getUsername())) {
            throw new IllegalArgumentException();
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpReqDto.getPassword());
        return MemberTokenResDto.from(memberRepository.save(signUpReqDto.toEntity(encodedPassword)));
    }

    @Transactional
    public JwtToken generateAccessToken(RefreshTokenReqDto refreshTokenReqDto) {
        if (!tokenRepository.existsByRefreshToken(refreshTokenReqDto.refreshToken()) || !jwtTokenProvider.validateToken(refreshTokenReqDto.refreshToken())) {
            throw new InvalidTokenException();
        }

        Token token = tokenRepository.findByRefreshToken(refreshTokenReqDto.refreshToken()).orElseThrow();
        Member member = memberRepository.findById(token.getMember().getMemberId()).orElseThrow(MemberNotFoundException::new);

        return jwtTokenProvider.generateAccessTokenByRefreshToken(member.getUsername(), token.getRefreshToken());
    }

}
