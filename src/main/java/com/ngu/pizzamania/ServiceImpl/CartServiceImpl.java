package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Exception.OutOfOrderQuantityException;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.*;
import com.ngu.pizzamania.Repository.CartItemRepository;
import com.ngu.pizzamania.Repository.CartRepository;
import com.ngu.pizzamania.Repository.PizzaRepository;
import com.ngu.pizzamania.Repository.UserRepository;
import com.ngu.pizzamania.Service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Cart addToCart(Pizza pizza,User user, Integer quantity) throws OutOfOrderQuantityException {
       Cart cart = cartRepository.findByUser(user);
       if(cart == null){
           cart = new Cart();
           cart.setUser(user);
       }
        CartItem cartItem = cartItemRepository.findCartItemByPizzaAndUser(pizza,user);
       if(cartItem == null){
           cartItem = new CartItem();
           cartItem.setCart(cart);
           cartItem.setPizza(pizza);
           cartItem.setUser(user);
           cartItem.setQuantity(quantity);
           cartItem.setSubTotal(pizza.getPrice() * quantity);
           cart.getCartItems().add(cartItem);
       } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setSubTotal(pizza.getPrice() * cartItem.getQuantity());
       }
        double totalAmount = 0.0;
       for (CartItem item : cart.getCartItems()){
           totalAmount += item.getSubTotal();
       }
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }



    @Override
    public Cart deleteCartItemById(int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorResponse.builder().message("User not found").build()));
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getCartItem_id().equals(id))
                    .findFirst();
            if (optionalCartItem.isPresent()) {
                CartItem cartItem = optionalCartItem.get();
                cart.setTotalAmount(cart.getTotalAmount() - cartItem.getSubTotal());
                cart.getCartItems().remove(cartItem);
                cartRepository.save(cart);
            } else {
                throw new ResourceNotFoundException(ErrorResponse.builder().message("Cart item not found").build());
            }
        } else {
            throw new ResourceNotFoundException(ErrorResponse.builder().message("Cart not found").build());
        }
        return cart;
    }
}
