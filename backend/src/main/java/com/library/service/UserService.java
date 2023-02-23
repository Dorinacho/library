package com.library.service;


import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.exceptions.ResourceNotFoundException;
import com.library.mapper.UserMapper;
import com.library.models.User;
import com.library.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<String> getUsernames() {
        return userRepository.findAll().stream()
                .map(User::getUsername)
                .toList();
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Could not find user with id: " + id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserCreationDTO userCreationDTO) {
        boolean userExists = userRepository.findByUsername(userCreationDTO.getUsername()).isPresent();
        if (!userExists) {
            userCreationDTO.setPassword(bCryptPasswordEncoder.encode(userCreationDTO.getPassword()));
            User user = userMapper.toUser(userCreationDTO);
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("Username is taken");
        }
    }

    public void updateUser(User userData, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user!"));
        System.out.println(userData.toString());
        System.out.println(user.toString());
        if (userData.getName() != null) {
            user.setName(userData.getName());
        }
        if (userData.getUsername() != null) {
            user.setUsername(userData.getUsername());
        }
        if (userData.getName() != null) {
            user.setEmail(userData.getEmail());
        }
        if (!userData.getLoans().isEmpty()) {
            user.setLoans(userData.getLoans());
        }
        if (!userData.getRoles().isEmpty()) {
            user.setRoles(userData.getRoles());
        }
        System.out.println(user);
        userRepository.save(user);
    }

    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }

    public ResponseEntity<String> checkUsername(String username) {
        boolean isTaken = userRepository.existsByUsername(username);
        if (isTaken) {
            return ResponseEntity.badRequest().body("Username is taken");
        }

        return ResponseEntity.ok("Username is not taken");
    }
}
