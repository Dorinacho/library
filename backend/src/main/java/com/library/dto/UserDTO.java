package com.library.dto;


import lombok.Data;

public class UserDTO {
    private String name;
    private String username;
    private String email;

    public UserDTO(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
