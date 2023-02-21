package com.library.controllers;

import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.models.User;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/usernames")
    public List<String> getUsernames() {
        return userService.getUsernames();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public User getUserByID(@PathVariable(name = "id") Long id) {
        return userService.getUserByID(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void addUser(@RequestBody UserCreationDTO userCreationDTO) {
        userService.addUser(userCreationDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void updateUser(@RequestBody User user, @PathVariable Long id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public void deleteUser(@PathVariable(name = "id") Long userID) {
        userService.deleteUser(userID);
    }
}
