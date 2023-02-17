package com.library.security.jwt;

import com.library.model.ERole;

import java.util.List;

public class JwtResponse {
    private final List<ERole> roles;
    private String username;
    private final String token;
    private String type = "Bearer";

    private boolean isAdmin;

    private boolean isMod;


    public JwtResponse(String accessToken, String username, List<ERole> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
        for (ERole role : roles) {
            if (role.equals(ERole.ROLE_ADMIN)) {
                this.isAdmin = true;
            } else if (role.equals(ERole.ROLE_MODERATOR)) {
                this.isMod = true;
            } else {
                this.isAdmin = this.isMod = false;
            }
        }
    }

    public String getAccessToken() {
        return token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ERole> getRoles() {
        return roles;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isMod() {
        return isMod;
    }
}
