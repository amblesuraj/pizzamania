package com.ngu.pizzamania.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.*;
import com.ngu.pizzamania.Service.EmailService;
import com.ngu.pizzamania.Service.RoleService;
import com.ngu.pizzamania.Service.UserService;
import com.ngu.pizzamania.ServiceImpl.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtService jwtService;
    @Autowired
    EmailService emailService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/save")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
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
    @Autowired
    PasswordEncoder passwordEncoder;
    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUsers(@PathVariable Integer id, HttpServletRequest request) throws IOException {
        User currentUser = userService.findById(id).orElse(null);
        String encodedPassword = currentUser.getPassword();
        User updatedUser = objectMapper.readerForUpdating(currentUser).readValue(request.getReader());

        if(currentUser!=null && !updatedUser.getPassword().equals(encodedPassword)){
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userService.updateUser(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthRequest authRequest){
        Authentication authenticate;
        if(authRequest.getUsername() != null){
        authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        }
        else {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
        }

        Map<String,Object> data = new HashMap<>();
        final Optional<User> userOptional = userService.findByUsernameOrEmail(authRequest.getUsername(),authRequest.getEmail());
        if(userOptional.isPresent()){
            data.put("user",userOptional.get());
            data.put("token",jwtService.generateToken(authRequest.getUsername()));
        }
        if(authenticate.isAuthenticated()){
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .data(data)
                            .message("token generated")
                            .timeStamp(new Date())
                            .statusCode(HttpStatus.OK.value())
                            .httpStatus(HttpStatus.OK)
                            .build()
            );
        }
       else {
           throw new ResourceNotFoundException(ErrorResponse.builder()
                   .message("User not found").build());
        }

    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("amblesuraj@gmail.com");
        message.setTo("surajamble1211@gmail.com");
        message.setSubject("Simple test message");
        message.setText("message for testing ");
        emailService.SendEmail(message);
        return ResponseEntity.ok("Email sent successfully");
    }
}
