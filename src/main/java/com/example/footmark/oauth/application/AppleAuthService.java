package com.example.footmark.oauth.application;

import com.example.footmark.member.domain.SocialType;
import com.example.footmark.oauth.api.dto.res.UserInfo;
import com.example.footmark.oauth.exception.OAuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Transactional(readOnly = true)
public class AppleAuthService implements OAuthService {

    private static final String JWT_DELIMITER = "\\.";

    private final ObjectMapper objectMapper;

    public AppleAuthService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String getProvider() {
        return String.valueOf(SocialType.APPLE).toLowerCase();
    }

    @Transactional
    @Override
    public UserInfo getUserInfo(String idToken) {
        String decodePayload = getDecodePayload(idToken);

        try {
            return objectMapper.readValue(decodePayload, UserInfo.class);
        } catch (JsonProcessingException e) {
            throw new OAuthException();
        }
    }

    private String getDecodePayload(String idToken) {
        String payload = getPayload(idToken);

        return new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8);
    }

    private String getPayload(String idToken) {
        return idToken.split(JWT_DELIMITER)[1];
    }

}
