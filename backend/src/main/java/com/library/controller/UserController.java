package com.library.controller;

import com.library.dto.UserCreationDTO;
import com.library.dto.UserDTO;
import com.library.model.User;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable(name = "id") Long id) {
        return userService.getUserByID(id);
    }

//    @PostMapping
//    public void addUser(@RequestBody UserCreationDTO userCreationDTO) {
//        userService.addUser(userCreationDTO);
//    }

    @PutMapping("/{id}")
    public void updateUser(@RequestBody User user, @PathVariable(name = "id") Long userID) {
        userService.updateUser(user, userID);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") Long userID) {
        userService.deleteUser(userID);
    }
}
