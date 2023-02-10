package com.library.mapper;

import com.library.DTOs.UserCreationDTO;
import com.library.DTOs.UserDTO;
import com.library.model.Role;
import com.library.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        String name = user.getName();
//        String email = user.getEmail();
//        String username = user.getUsername();
        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(toList());

        return new UserDTO(name, roles);
    }

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword(), new ArrayList<>());
    }
}
