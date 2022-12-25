package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);
    List<Role> findAllRoles();

    Role findById(Integer id);
}
