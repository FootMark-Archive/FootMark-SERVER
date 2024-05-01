package com.example.footmark.oauth.application;

import com.example.footmark.global.jwt.api.dto.res.JwtToken;
import com.example.footmark.global.jwt.domain.Token;
import com.example.footmark.global.jwt.domain.repository.TokenRepository;
import com.example.footmark.member.domain.repository.MemberRepository;
import com.example.footmark.oauth.OAuthJwtTokenProvider;
import com.example.footmark.oauth.api.dto.res.MemberLoginResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuthTokenService {
    private final OAuthJwtTokenProvider oauthjwttokenProvider;
    private final TokenRepository tokenRepository;
//    private final MemberRepository memberRepository;

    @Transactional
    public JwtToken getToken(MemberLoginResDto memberLoginResDto) {
        JwtToken tokenDto = oauthjwttokenProvider.generateToken(memberLoginResDto.findMember().getUsername());

        tokenSaveAndUpdate(memberLoginResDto, tokenDto);

        return tokenDto;
    }

    private void tokenSaveAndUpdate(MemberLoginResDto memberLoginResDto, JwtToken tokenDto) {
        if (!tokenRepository.existsByMember(memberLoginResDto.findMember())) {
            tokenRepository.save(Token.builder()
                    .member(memberLoginResDto.findMember())
                    .refreshToken(tokenDto.refreshToken())
                    .build());
        }

        refreshTokenUpdate(memberLoginResDto, tokenDto);
    }

    private void refreshTokenUpdate(MemberLoginResDto memberLoginResDto, JwtToken tokenDto) {
        Token token = tokenRepository.findByMember(memberLoginResDto.findMember()).orElseThrow();
        token.updateToken(tokenDto.refreshToken());
    }

}
