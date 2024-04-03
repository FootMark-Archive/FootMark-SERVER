package com.example.footmark.global.jwt.api;

import com.example.footmark.global.jwt.api.dto.req.RefreshTokenReqDto;
import com.example.footmark.global.jwt.api.dto.res.JwtToken;
import com.example.footmark.global.jwt.api.dto.req.SignInReqDto;
import com.example.footmark.global.jwt.api.dto.req.SignUpReqDto;
import com.example.footmark.global.jwt.api.dto.res.MemberTokenResDto;
import com.example.footmark.global.jwt.application.TokenService;
import com.example.footmark.global.template.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class AuthController {

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/sign-in")
    public RspTemplate<JwtToken> signIn(@RequestBody SignInReqDto signInReqDto) {
        String username = signInReqDto.getUsername();
        String password = signInReqDto.getPassword();
        JwtToken jwtToken = tokenService.signIn(username, password);

        return new RspTemplate<>(HttpStatus.OK, "로그인 성공", jwtToken);
    }

    @PostMapping("/sign-up")
    public RspTemplate<MemberTokenResDto> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        MemberTokenResDto memberTokenResDto = tokenService.signUp(signUpReqDto);
        return new RspTemplate<>(HttpStatus.OK, "회원가입 성공", memberTokenResDto);
    }

    @PostMapping("/access")
    public RspTemplate<JwtToken> generateAccessToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto) {
        JwtToken getToken = tokenService.generateAccessToken(refreshTokenReqDto);

        return new RspTemplate<>(HttpStatus.OK, "액세스 토큰 발급", getToken);
    }



}
