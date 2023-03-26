package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ApiResponse;
import com.ngu.pizzamania.Model.AuthRequest;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Service.UserService;
import com.ngu.pizzamania.ServiceImpl.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {
private final UserService userService;


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
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
}
