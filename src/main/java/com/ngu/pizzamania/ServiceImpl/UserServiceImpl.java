package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Model.SecurityUser;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Repository.RoleRepository;
import com.ngu.pizzamania.Repository.UserRepository;
import com.ngu.pizzamania.Service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found ::"+username));
        return new SecurityUser(user);
    }

    @Override
    public User createUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").orElse(new Role("ROLE_USER"));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        user.addRole(userRole);
        user.addRole(adminRole);
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


}
