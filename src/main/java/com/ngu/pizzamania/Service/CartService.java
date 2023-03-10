package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Exception.OutOfOrderQuantityException;
import com.ngu.pizzamania.Model.Cart;
import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Model.User;

public interface CartService {

    Cart addToCart(Pizza pizza, User user, Integer quantity) throws OutOfOrderQuantityException;

    Cart updateCart(Pizza pizza,User user, Integer quantity);

    void deleteById(int id);

}
