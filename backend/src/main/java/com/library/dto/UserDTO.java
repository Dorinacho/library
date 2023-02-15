package com.library.dto;

import com.library.model.ERole;

import java.util.List;

public class UserDTO {

    private String username;
    private List<ERole> roles;

    public UserDTO(String username, List<ERole> roles) {
        this.username = username;
        this.roles = roles;
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

    public void setRoles(List<ERole> roles) {
        this.roles = roles;
    }
}
