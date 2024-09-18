package com.company.projectmgt.api.services;

import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

@Service
@RestService("google")
public class GoogleAuthService {

    private static final Logger log = LoggerFactory.getLogger(GoogleAuthService.class);

    @RestMethod
    public Map<String, Object> getUserInfo(OAuth2AuthenticationToken authentication) {
        if (authentication == null) {
            log.error("Authentication token is null. OAuth2 authentication may not have completed successfully.");
            return Collections.singletonMap("error", "Authentication failed: No valid authentication token");
        }

        Object principal = authentication.getPrincipal();
        if (principal == null || !(principal instanceof DefaultOidcUser)) {
            log.error("Unexpected principal type: {}", principal != null ? principal.getClass().getName() : "null");
            return Collections.singletonMap("error", "Unexpected authentication type");
        }

        DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
        return oidcUser.getClaims();
    }
}