package com.gavrilov.plants.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_WORKER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}