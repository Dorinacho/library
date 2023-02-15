package com.library.mapper;

import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.model.ERole;
import com.library.model.Role;
import com.library.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        String username = user.getUsername();
        List<ERole> roles = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return new UserDTO(username, roles);
    }

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());
    }
}
