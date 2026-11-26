package com.palette.bff.api;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/protected/session")
    public Map<String, Object> protectedSession(Authentication authentication) {
        return Map.of(
                "authenticated", authentication.isAuthenticated(),
                "principal", authentication.getName()
        );
    }

    @GetMapping("/admin/status")
    public Map<String, String> adminStatus() {
        return Map.of("status", "authorized");
    }
}
