package com.library.service;


import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.exceptions.ResourceNotFoundException;
import com.library.mapper.UserMapper;
import com.library.models.User;
import com.library.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> getUserByID(Long id) {
        return userRepository.findById(id);
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void addUser(UserCreationDTO userCreationDTO) throws HttpClientErrorException {
//        boolean userExists = userRepository.findByUsername(userCreationDTO.getUsername()).isPresent();
//        if (!userExists) {
//            if (userCreationDTO.getName() != null && userCreationDTO.getUsername() != null && userCreationDTO.getEmail() != null && userCreationDTO.getPassword() != null) {
//                userCreationDTO.setPassword(bCryptPasswordEncoder.encode(userCreationDTO.getPassword()));
//                User user = userMapper.toUser(userCreationDTO);
//                userRepository.save(user);
//            } else {
//                throw new HttpClientErrorException(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), "Some fields are null");
//            }
//        } else {
//            throw new ResourceNotFoundException("Username is taken");
//        }
//    }

    public User updateUser(User userData, Long id) {
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
        return userRepository.save(user);
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
