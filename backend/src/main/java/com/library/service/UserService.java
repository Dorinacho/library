package com.library.service;


import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.mapper.UserMapper;
import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserCreationDTO userCreationDTO) {
        userCreationDTO.setPassword(bCryptPasswordEncoder.encode(userCreationDTO.getPassword()));
        User user = userMapper.toUser(userCreationDTO);
        userRepository.save(user);
    }

    public void updateUser(User userData, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find user!"));
        if (userData.getName() != null) {
            user.setName(userData.getName());
        }
        if (userData.getUsername() != null) {
            user.setUsername(userData.getUsername());
        }
        if (userData.getName() != null) {
            user.setEmail(userData.getEmail());
        }
        if (userData.getLoans() != null) {
            user.setLoans(userData.getLoans());
        }
        if (userData.getRoles() != null) {
            user.setRoles(userData.getRoles());
        }
        userRepository.save(user);
    }

    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }
}
