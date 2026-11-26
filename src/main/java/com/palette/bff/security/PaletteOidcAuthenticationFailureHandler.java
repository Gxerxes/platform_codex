package com.palette.bff.security;

import java.io.IOException;

import com.palette.bff.error.ApiProblem;
import com.palette.bff.observability.CorrelationIdFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class PaletteOidcAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public PaletteOidcAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String correlationId = (String) request.getAttribute(CorrelationIdFilter.CORRELATION_ID_ATTRIBUTE);
        if (correlationId == null) {
            correlationId = "unavailable";
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(),
                ApiProblem.of(HttpStatus.UNAUTHORIZED, "Unauthorized",
                        "OIDC authentication failed.", correlationId));
    }
}
