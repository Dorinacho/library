package com.library.mapper;

import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        String name = user.getName();
        String username = user.getUsername();
        String email = user.getEmail();

        return new UserDTO(name, username, email);
    }

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());
    }
}
