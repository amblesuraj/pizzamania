package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Repository.RoleRepository;
import com.ngu.pizzamania.Service.RoleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

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
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if(role == null){
            role = new Role("ROLE_SUPERADMIN");
            roleRepository.save(role);
        }
        return role;
        
    }

    @Override
    public boolean existsByRoleName(String name) {
        return roleRepository.existsByName(name);
    }
}
