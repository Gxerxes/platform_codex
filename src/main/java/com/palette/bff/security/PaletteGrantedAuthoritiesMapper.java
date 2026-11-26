package com.palette.bff.security;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

public class PaletteGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Collection<GrantedAuthority> mapped = new LinkedHashSet<>(authorities);
        boolean oidcAuthorityPresent = authorities.stream()
                .anyMatch(authority -> authority instanceof OidcUserAuthority);
        if (oidcAuthorityPresent) {
            mapped.add(new SimpleGrantedAuthority("OIDC_USER"));
        }
        return mapped;
    }
}
