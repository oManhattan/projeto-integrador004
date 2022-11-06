package com.pi.model.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserAuthority {
    
    PROFISSIONAL("ROLE_PROFISSIONAL"),
    CLIENTE("ROLE_CLIENTE");

    private final String authority;

    private UserAuthority(String authority) {
        this.authority = authority;
    }

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(authority);
    }
}
