package com.palette.bff.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("oauth2")
@Configuration
public class PaletteOidcSecurityConfig {

    private final ObjectMapper objectMapper;

    public PaletteOidcSecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    @Order(1)
    SecurityFilterChain oidcSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/protected/**", "/api/admin/**", "/api/auth/**",
                        "/oauth2/**", "/login/**")
                .cors(Customizer.withDefaults())
                .oauth2Login(oauth -> oauth
                        .successHandler(new PaletteOidcAuthenticationSuccessHandler())
                        .failureHandler(new PaletteOidcAuthenticationFailureHandler(objectMapper)))
                .logout(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/protected/**").hasAuthority("OIDC_USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/**", "/oauth2/**", "/login/**").permitAll())
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, exception) ->
                                response.sendRedirect("/oauth2/authorization/keycloak")));

        return http.build();
    }

    @Bean
    PaletteGrantedAuthoritiesMapper paletteGrantedAuthoritiesMapper() {
        return new PaletteGrantedAuthoritiesMapper();
    }
}
