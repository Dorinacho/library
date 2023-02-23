package com.library.service;

import com.library.exceptions.ResourceNotFoundException;
import com.library.models.Role;
import com.library.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRole(Role role) {
        return roleRepository.findById(role.getId()).orElseThrow(() ->
                new ResourceNotFoundException("Could not fin role!")
    );

    }
}
