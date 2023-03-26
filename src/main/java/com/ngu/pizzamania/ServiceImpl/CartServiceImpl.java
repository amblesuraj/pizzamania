package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Exception.OutOfOrderQuantityException;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.*;
import com.ngu.pizzamania.Repository.CartItemRepository;
import com.ngu.pizzamania.Repository.CartRepository;
import com.ngu.pizzamania.Repository.UserRepository;
import com.ngu.pizzamania.Service.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    
    final private CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;


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
           checkMaxQuantity(cartItem, quantity);
           cartItem.setSubTotal(pizza.getPrice() * quantity);
           cart.getCartItems().add(cartItem);
       } else {
           checkMaxQuantity(cartItem, cartItem.getQuantity() + quantity);
           cartItem.setSubTotal(pizza.getPrice() * cartItem.getQuantity());
       }
        double totalAmount = 0.0;
       for (CartItem item : cart.getCartItems()){
           totalAmount += item.getSubTotal();
       }
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }

    private static void checkMaxQuantity(CartItem cartItem, int quantity) {
            if(quantity > 10 || (cartItem.getQuantity() != null && cartItem.getQuantity() > 10) || (cartItem.getQuantity() != null && (cartItem.getQuantity() + quantity) > 10)){
                throw new OutOfOrderQuantityException(ErrorResponse.builder().message("Quantity should be at least 1 and at most 10.").build());
            } else{
                 cartItem.setQuantity(quantity);
            }
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
