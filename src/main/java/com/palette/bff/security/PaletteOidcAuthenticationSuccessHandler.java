package com.palette.bff.security;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class PaletteOidcAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String subject = authentication.getName();
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            subject = oidcUser.getSubject();
        }

        response.getWriter().write("{\"authenticated\":true,\"subject\":\""
                + subject.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
    }
}
