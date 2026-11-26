package com.palette.bff.security;

import java.util.List;

import com.palette.bff.error.ApiProblem;
import com.palette.bff.observability.CorrelationIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class PaletteSecurityConfig {

    private final SecurityProperties securityProperties;

    public PaletteSecurityConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/actuator/health", "/actuator/health/**"))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/hello", "/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers("/actuator/info").permitAll()
                        .requestMatchers("/api/protected/**").authenticated()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, exception) ->
                                writeProblem(request, response, HttpStatus.UNAUTHORIZED,
                                        "Unauthorized", "Authentication is required."))
                        .accessDeniedHandler((request, response, exception) ->
                                writeProblem(request, response, HttpStatus.FORBIDDEN,
                                        "Forbidden", "The authenticated principal lacks required permissions.")));

        http.headers(headers -> headers
                .frameOptions(frame -> frame.deny())
                .contentTypeOptions(Customizer.withDefaults())
                .xssProtection(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(securityProperties.cors().allowedOrigins());
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Correlation-ID", "X-CSRF-TOKEN"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    private void writeProblem(HttpServletRequest request,
                              jakarta.servlet.http.HttpServletResponse response,
                              HttpStatus status, String title, String detail) throws java.io.IOException {
        String correlationId = (String) request.getAttribute(CorrelationIdFilter.CORRELATION_ID_ATTRIBUTE);
        if (correlationId == null) {
            correlationId = "unavailable";
        }
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper()
                .findAndRegisterModules()
                .writeValueAsString(ApiProblem.of(status, title, detail, correlationId)));
    }
}
