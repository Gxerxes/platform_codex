package com.palette.bff.api;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/session")
    public Map<String, Object> session(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof OidcUser oidcUser)) {
            return Map.of(
                    "authenticated", authentication.isAuthenticated(),
                    "principal", authentication.getName()
            );
        }

        return Map.of(
                "authenticated", authentication.isAuthenticated(),
                "subject", oidcUser.getSubject(),
                "preferredUsername", oidcUser.getPreferredUsername() == null
                        ? authentication.getName() : oidcUser.getPreferredUsername()
        );
    }
}
