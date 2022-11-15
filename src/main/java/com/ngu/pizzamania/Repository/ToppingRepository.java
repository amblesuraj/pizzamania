package com.ngu.pizzamania.Repository;

import com.ngu.pizzamania.Model.Topping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Integer> {
}
