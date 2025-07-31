package com.uk.spring_security_phase1.models;

public class AuthenticationRequest {

    private String username;
    private String password;

    public AuthenticationRequest(final String usernameIn, final String passwordIn) {
        this.username = usernameIn;
        this.password = passwordIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
