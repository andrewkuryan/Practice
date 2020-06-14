package org.fekz115.task8.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN, MANAGER, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
