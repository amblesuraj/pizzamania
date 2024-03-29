package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.OutOfOrderQuantityException;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.*;
import com.ngu.pizzamania.Service.CartService;
import com.ngu.pizzamania.Service.PizzaService;
import com.ngu.pizzamania.Service.UserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CartController {


    private final CartService cartService;


    private final PizzaService pizzaService;


    private final UserService userService;


    private final MessageSource messageSource;
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
    
    @DeleteMapping("/delete/cartItem/{id}")
    public ResponseEntity<ApiResponse> deleteCartItemById(@PathVariable Integer id){
        Cart cart = cartService.deleteCartItemById(id);
        return ResponseEntity.ok()
                .body(ApiResponse
                        .builder()
                        .message("CartItem with id "+id +" deleted")
                        .data(cart)
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .timeStamp(new Date())
                        .build());
    }

    @GetMapping("/userCarts")
    public ResponseEntity<Object> getUserCarts(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         Set<CartItem> cartItem = null;
         Optional<User> optionalUser = userService.findByUsername(authentication.getName());
        if(optionalUser.isPresent()){
           User user = optionalUser.get();
           cartItem  = user.getCartItem();
        }
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .message("Usercarts retrieved successfully")
                        .data(cartItem)
                        .timeStamp(new Date())
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
