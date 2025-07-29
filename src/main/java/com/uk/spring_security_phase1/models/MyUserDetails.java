package com.uk.spring_security_phase1.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private List<SimpleGrantedAuthority> authorities;

    public MyUserDetails(final UserEntity userEntityIn) {
        this.userName = userEntityIn.getUsername();
        this.password = userEntityIn.getPassword();
        this.active = userEntityIn.isActive();
        this.authorities =
                Arrays.stream(userEntityIn.getRoles().split(",")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
