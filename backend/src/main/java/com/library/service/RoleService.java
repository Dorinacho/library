package com.library.service;

import com.library.model.Role;
import com.library.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return roleRepository.findById(role.getId()).orElseThrow(NoSuchElementException::new);

    }
}
