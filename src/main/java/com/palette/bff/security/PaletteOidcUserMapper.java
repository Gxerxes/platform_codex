package com.palette.bff.security;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class PaletteOidcUserMapper {

    public OidcUserMap map(OidcUser user) {
        Set<String> roles = extractRoles(user.getClaims());
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        authorities.add(new SimpleGrantedAuthority("OIDC_USER"));

        String subject = user.getSubject();
        String username = claimAsString(user.getClaims(), "preferred_username", subject);
        return new OidcUserMap(subject, username, authorities);
    }

    private Set<String> extractRoles(Map<String, Object> claims) {
        Object realmAccess = claims.get("realm_access");
        if (realmAccess instanceof Map<?, ?> realm) {
            Object roles = realm.get("roles");
            if (roles instanceof Collection<?> values) {
                return values.stream()
                        .filter(value -> value instanceof String)
                        .map(String.class::cast)
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            }
        }
        return Set.of();
    }

    private String claimAsString(Map<String, Object> claims, String claim, String fallback) {
        Object value = claims.get(claim);
        return value instanceof String text && !text.isBlank() ? text : fallback;
    }

    public record OidcUserMap(String subject, String username,
                              Collection<GrantedAuthority> authorities) {
    }
}
