package com.library.security.jwt;

import com.library.model.ERole;

import java.util.List;

public class JwtResponse {
    private final List<ERole> roles;
    private String username;
    private String token;
    private String type = "Bearer";

    public JwtResponse(String username, List<ERole> roles) {
        this.username = username;
        this.roles = roles;
    }

    public JwtResponse(String accessToken, String username, List<ERole> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
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
}
