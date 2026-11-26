package com.palette.bff.security;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import static org.assertj.core.api.Assertions.assertThat;

class PaletteGrantedAuthoritiesMapperTests {

    @Test
    void addsOidcUserAuthorityForOidcAuthentication() {
        OidcIdToken token = OidcIdToken.withTokenValue("token")
                .subject("keycloak-user-1")
                .claim("preferred_username", "palette.user")
                .build();
        OidcUserAuthority oidcAuthority = new OidcUserAuthority(token);

        var mapped = new PaletteGrantedAuthoritiesMapper()
                .mapAuthorities(List.of(oidcAuthority));

        assertThat(mapped.stream().map(authority -> authority.getAuthority()).toList())
                .contains("OIDC_USER");
    }
}
