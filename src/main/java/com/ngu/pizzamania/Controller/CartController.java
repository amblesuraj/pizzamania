package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.OutOfOrderQuantityException;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.*;
import com.ngu.pizzamania.Service.CartService;
import com.ngu.pizzamania.Service.PizzaService;
import com.ngu.pizzamania.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    PizzaService pizzaService;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;
    @PostMapping("/addToCart/{pizzaId}")
    public ResponseEntity<Object> addToCart(@PathVariable Integer pizzaId, @RequestParam Integer quantity) throws OutOfOrderQuantityException {
        Pizza pizza =null;
        User user = null;
        Cart cart = null;
         Optional<Pizza> optionalPizza = pizzaService.getPizzaById(pizzaId);
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         if (authentication != null){
            Optional<User> optionalUser = userService.findByUsername(authentication.getName());
             if(optionalPizza.isPresent() && optionalUser.isPresent()){
                 pizza = optionalPizza.get();
                 user = optionalUser.get();
             }
            cart = cartService.addToCart(pizza, user, quantity);
       }
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("Added to cart")
                        .data(cart)
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .timeStamp(new Date())
                        .build()
                );
    }
}
