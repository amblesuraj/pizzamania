package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Repository.RoleRepository;
import com.ngu.pizzamania.Repository.UserRepository;
import com.ngu.pizzamania.Service.RoleService;
import com.ngu.pizzamania.Service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role superadminRole = roleService.findByName("ROLE_USER");
        user.addRole(superadminRole);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void DeleteUser(Integer id) {

    }

    @Override
    public boolean existsByUsernameOREmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username,email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
