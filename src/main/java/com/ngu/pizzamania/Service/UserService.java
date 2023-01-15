package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    User createUser(User user);
    User updateUser(User user);
    List<User> findAllUsers();
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findById(Integer id);
    void DeleteUser(Integer id);

    boolean existsByUsernameOREmail(String username, String email);
}
