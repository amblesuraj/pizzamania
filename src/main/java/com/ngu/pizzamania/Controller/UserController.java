package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ApiResponse;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Service.RoleService;
import com.ngu.pizzamania.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        boolean userExists = userService.existsByUsernameOREmail(user.getUsername(), user.getEmail());
        if (userExists) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message("User already exists with given username or email")
                            .httpStatus(HttpStatus.CONFLICT)
                            .statusCode(HttpStatus.CONFLICT.value())
                            .timeStamp(new Date()).build()
            );
        }

        return ResponseEntity.ok().body(ApiResponse.builder()
                .data(userService.createUser(user))
                .message("User with name " + user.getUsername() + "created successfully")
                .httpStatus(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .timeStamp(new Date()).build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException(ErrorResponse.
                    builder()
                    .message("User List not Found")
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .data(users)
                .httpStatus(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                .timeStamp(new Date()).build());
    }


    @GetMapping("/allRoles")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        if (roles.isEmpty()) {
            throw new ResourceNotFoundException(ErrorResponse.
                    builder()
                    .message("Roles List not Found")
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.builder()
                .data(roles)
                .httpStatus(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                .timeStamp(new Date()).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(ErrorResponse
                        .builder().message("User with id " + id + " does not exists").build())));
    }

    @GetMapping("/role/{id}")
    public Role getRolesById(@PathVariable Integer id){
        return roleService.findById(id);
    }
}
