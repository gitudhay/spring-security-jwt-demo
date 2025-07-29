package com.uk.spring_security_phase1.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private boolean active;
    private String roles;

    public int getId() {
        return id;
    }

    public void setId(final int idIn) {
        id = idIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String usernameIn) {
        username = usernameIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String passwordIn) {
        password = passwordIn;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean activeIn) {
        active = activeIn;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(final String rolesIn) {
        roles = rolesIn;
    }
}
