package com.ngu.pizzamania.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ApiResponse;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Service.RoleService;
import com.ngu.pizzamania.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModelMapper modelMapper;

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

    @PostMapping("/role")
    public ResponseEntity<?> createRole(@RequestBody Role role) {

        boolean existsRole = roleService.existsByRoleName(role.getName());

        if (existsRole) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message("Role name is already exists. Try different.")
                            .httpStatus(HttpStatus.CONFLICT)
                            .statusCode(HttpStatus.CONTINUE.value())
                            .timeStamp(new Date()).build());
        }
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .data(roleService.createRole(role))
                        .message("Role with name " + role.getName() + " Created usccessfully")
                        .httpStatus(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .timeStamp(new Date()).build()
        );
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
    public Role getRolesById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @Autowired
    ObjectMapper objectMapper;
    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUsers(@PathVariable Integer id, HttpServletRequest request) throws IOException {
        User currentUser = userService.findById(id).orElse(null);
        User updatedUser = objectMapper.readerForUpdating(currentUser).readValue(request.getReader());
        userService.updateUser(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }
}
