package com.library.service;


import com.library.DTOs.UserCreationDTO;
import com.library.DTOs.UserDTO;
import com.library.mapper.UserMapper;
import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    private RoleService roleService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserMapper userMapper;

    public List<UserDTO> getUsers() {
        return userRepository.getUsers().stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserCreationDTO userCreationDTO) {
        User user = userMapper.toUser(userCreationDTO);

//        user.setPassword(bCryptPasswordEncoder
//                .encode(user.getPassword()));
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
