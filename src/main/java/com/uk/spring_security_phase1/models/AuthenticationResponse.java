package com.uk.spring_security_phase1.models;

public class AuthenticationResponse {

    private String jwt;

    public AuthenticationResponse(String jwtIn) {
        this.jwt = jwtIn;
    }

    public String getJwt() {
        return jwt;
    }
}
