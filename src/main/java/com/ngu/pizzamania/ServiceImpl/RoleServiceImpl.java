package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Repository.RoleRepository;
import com.ngu.pizzamania.Service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        role.setName("ROLE_"+role.getName().toUpperCase());
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ErrorResponse.builder().message("ID not found").build()));
    }

    @Override
    public boolean existsByRoleName(String name) {
        return roleRepository.existsByName(name);
    }
}
