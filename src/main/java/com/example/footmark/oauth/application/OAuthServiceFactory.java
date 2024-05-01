package com.example.footmark.oauth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OAuthServiceFactory {
    private final Map<String, OAuthService> oauthServiceMap;

    @Autowired
    public OAuthServiceFactory(List<OAuthService> oauthServiceList) {
        oauthServiceMap = new HashMap<>();
        for (OAuthService oauthService : oauthServiceList) {
            oauthServiceMap.put(oauthService.getProvider(), oauthService);
        }
    }

    public OAuthService getOAuthService(String provider) {
        return oauthServiceMap.get(provider);
    }
}
