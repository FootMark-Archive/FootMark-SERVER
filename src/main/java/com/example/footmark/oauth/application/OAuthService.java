package com.example.footmark.oauth.application;

import com.example.footmark.oauth.api.dto.res.UserInfo;

public interface OAuthService {
    UserInfo getUserInfo(String authCode);

    String getProvider();
}
