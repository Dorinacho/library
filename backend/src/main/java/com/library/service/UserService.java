package com.library.service;


import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public void addUser(User user) {
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
