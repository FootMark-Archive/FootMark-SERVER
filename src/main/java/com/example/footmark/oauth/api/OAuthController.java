package com.example.footmark.oauth.api;

import com.example.footmark.global.jwt.api.dto.res.JwtToken;
import com.example.footmark.global.template.RspTemplate;
import com.example.footmark.member.domain.SocialType;
import com.example.footmark.oauth.api.dto.req.TokenReqDto;
import com.example.footmark.oauth.api.dto.res.MemberLoginResDto;
import com.example.footmark.oauth.api.dto.res.UserInfo;
import com.example.footmark.oauth.application.OAuthMemberService;
import com.example.footmark.oauth.application.OAuthService;
import com.example.footmark.oauth.application.OAuthServiceFactory;
import com.example.footmark.oauth.application.OAuthTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthServiceFactory oauthServiceFactory;
    private final OAuthMemberService oauthmemberService;
    private final OAuthTokenService oauthtokenService;

    @Operation(summary = "소셜로그인 후 토큰 발급", description = "액세스, 리프레쉬 토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 발급 성공")
    })
    @PostMapping("/{provider}/token")
    public RspTemplate<JwtToken> generateAccessAndRefreshToken(
            @Parameter(name = "provider", description = "소셜 타입(google, apple)", in = ParameterIn.PATH)
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto) {

        OAuthService oauthService = oauthServiceFactory.getOAuthService(provider);
        UserInfo userInfo = oauthService.getUserInfo(tokenReqDto.authCode());

        MemberLoginResDto getMemberDto = oauthmemberService.saveUserInfo(userInfo, SocialType.valueOf(provider.toUpperCase()));
        JwtToken getToken = oauthtokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "토큰 발급", getToken);
    }
}
