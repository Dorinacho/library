package com.library.dto;

import com.library.model.ERole;

import java.util.List;

public class UserDTO {

    private String name;
    private List<ERole> roles;

    public UserDTO(String name, List<ERole> roles) {
        this.name = name;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ERole> getRoles() {
        return roles;
    }

    public void setRoles(List<ERole> roles) {
        this.roles = roles;
    }
}
