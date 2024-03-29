package com.ngu.pizzamania.Repository;

import com.ngu.pizzamania.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);

    boolean existsByName(String name);
}
