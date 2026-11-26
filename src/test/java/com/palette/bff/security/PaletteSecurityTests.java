package com.palette.bff.security;

import com.palette.bff.observability.CorrelationIdFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaletteSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicHelloEndpointIsAccessible() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .header(CorrelationIdFilter.HEADER_NAME, "security-public"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Correlation-ID", "security-public"));
    }

    @Test
    void protectedEndpointReturnsProblemDetail401() throws Exception {
        mockMvc.perform(get("/api/protected/session")
                        .header(CorrelationIdFilter.HEADER_NAME, "security-401"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.title").value("Unauthorized"))
                .andExpect(jsonPath("$.properties.correlationId").value("security-401"));
    }

    @Test
    @WithMockUser
    void authenticatedProtectedEndpointIsAccessible() throws Exception {
        mockMvc.perform(get("/api/protected/session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated").value(true));
    }

    @Test
    @WithMockUser
    void authenticatedNonAdminReturns403() throws Exception {
        mockMvc.perform(get("/api/admin/status"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.title").value("Forbidden"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminEndpointIsAccessible() throws Exception {
        mockMvc.perform(get("/api/admin/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("authorized"));
    }

    @Test
    @WithMockUser
    void postRequestRequiresCsrfToken() throws Exception {
        mockMvc.perform(post("/api/protected/session"))
                .andExpect(status().isForbidden());
    }

    @Test
    void actuatorHealthIsPublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }

    @Test
    void securityHeadersArePresent() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-Frame-Options", "DENY"));
    }

    @Test
    void corsAllowsConfiguredOrigin() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"));
    }
}
