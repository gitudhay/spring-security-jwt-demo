package com.uk.spring_security_phase1;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        return authentication;
    }
}
