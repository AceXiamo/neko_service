package com.xiamo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: AceXiamo
 * @ClassName: AnotherAuthenticationToken
 * @Date: 2023/3/3 22:47
 */
public class AnotherAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * The Principal.
     */
    private final Object principal;

    /**
     * Instantiates a new Another authentication token.
     *
     * @param principal   the principal
     * @param authorities the authorities
     */
    public AnotherAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
