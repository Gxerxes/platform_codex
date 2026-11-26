package com.palette.bff.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.security.oauth2.client.registration.keycloak.provider=keycloak",
        "spring.security.oauth2.client.registration.keycloak.client-id=palette-bff-test",
        "spring.security.oauth2.client.registration.keycloak.client-secret=test-secret",
        "spring.security.oauth2.client.registration.keycloak.authorization-grant-type=authorization_code",
        "spring.security.oauth2.client.registration.keycloak.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}",
        "spring.security.oauth2.client.registration.keycloak.scope=openid,profile,email",
        "spring.security.oauth2.client.provider.keycloak.authorization-uri=http://localhost:8180/realms/palette-placeholder/protocol/openid-connect/auth",
        "spring.security.oauth2.client.provider.keycloak.token-uri=http://localhost:8180/realms/palette-placeholder/protocol/openid-connect/token",
        "spring.security.oauth2.client.provider.keycloak.user-info-uri=http://localhost:8180/realms/palette-placeholder/protocol/openid-connect/userinfo",
        "spring.security.oauth2.client.provider.keycloak.jwk-set-uri=http://localhost:8180/realms/palette-placeholder/protocol/openid-connect/certs"
})
@ActiveProfiles("oauth2")
class PaletteOidcProfileTests {

    @Test
    void oauth2ProfileContextLoads() {
    }
}
