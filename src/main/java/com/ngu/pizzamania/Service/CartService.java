package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Exception.OutOfOrderQuantityException;
import com.ngu.pizzamania.Model.Cart;
import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Model.User;

public interface CartService {

    Cart addToCart(Pizza pizza, User user, Integer quantity) throws OutOfOrderQuantityException;


    Cart deleteCartItemById(int id);

}
