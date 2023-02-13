package com.library.service;


import com.library.DTOs.UserCreationDTO;
import com.library.DTOs.UserDTO;
import com.library.mapper.UserMapper;
import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
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
        user.setName(userData.getName());
        user.setEmail(userData.getEmail());
        user.setLoans(userData.getLoans());
        userRepository.save(user);
    }

    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }
}
