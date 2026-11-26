package com.palette.bff.security;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import static org.assertj.core.api.Assertions.assertThat;

class PaletteOidcUserMapperTests {

    @Test
    void mapsPreferredUsernameAndRealmRoles() {
        OidcIdToken token = OidcIdToken.withTokenValue("token")
                .subject("keycloak-user-1")
                .claim("preferred_username", "palette.user")
                .claim("realm_access", Map.of("roles", java.util.List.of("USER", "ADMIN")))
                .build();
        DefaultOidcUser oidcUser = new DefaultOidcUser(java.util.List.of(), token);

        var mapped = new PaletteOidcUserMapper().map(oidcUser);

        assertThat(mapped.subject()).isEqualTo("keycloak-user-1");
        assertThat(mapped.username()).isEqualTo("palette.user");
        assertThat(mapped.authorities()).extracting("authority")
                .contains("ROLE_USER", "ROLE_ADMIN", "OIDC_USER");
    }
}
