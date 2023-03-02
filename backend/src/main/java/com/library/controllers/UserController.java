package com.library.controllers;

import com.library.dto.UserDTO;
import com.library.models.User;
import com.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    Logger logger
            = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users;
        users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/usernames")
    public ResponseEntity<List<String>> getUsernames() {
        List<String> usernames;
        usernames = userService.getUsernames();
        if (usernames.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usernames, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<User> getUserByID(@PathVariable(name = "id") Long id) {
        if (userService.getUserByID(id).isPresent()) {
            return new ResponseEntity<>(userService.getUserByID(id).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User> addUser(@RequestBody UserCreationDTO userCreationDTO) {
//        try {
//            userService.addUser(userCreationDTO);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (Exception e) {
//            logger.error("Error => " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(name = "id") Long userID) {
        try {
            userService.deleteUser(userID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error => " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
