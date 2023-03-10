package com.ngu.pizzamania.Repository;

import com.ngu.pizzamania.Model.Cart;
import com.ngu.pizzamania.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
}
