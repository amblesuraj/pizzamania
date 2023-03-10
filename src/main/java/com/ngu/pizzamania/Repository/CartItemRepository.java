package com.ngu.pizzamania.Repository;

import com.ngu.pizzamania.Model.CartItem;
import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    CartItem findCartItemByPizzaAndUser(Pizza pizza, User user);
}
