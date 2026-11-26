package com.palette.bff.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "palette.security")
public record SecurityProperties(
        Cors cors
) {
    public record Cors(
            List<String> allowedOrigins
    ) {
        public Cors {
            allowedOrigins = allowedOrigins == null ? new ArrayList<>() : new ArrayList<>(allowedOrigins);
        }
    }
}
