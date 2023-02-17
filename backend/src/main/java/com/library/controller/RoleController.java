package com.library.controller;

import com.library.model.Role;
import com.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/roles")
@CrossOrigin(origins = "http://localhost:8080")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Role getRoleByID(@PathVariable(name = "id") Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

//    @PostMapping
//    public void addUser(@RequestBody UserCreationDTO userCreationDTO) {
//        userService.addUser(userCreationDTO);
//    }

//    @PutMapping("/{id}")
//    public void updateUser(@RequestBody User user, @PathVariable(name = "id") Long userID) {
//        userService.updateUser(user, userID);
//    }

}
