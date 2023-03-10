package com.ngu.pizzamania.Repository;

import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Model.Topping;
import org.hibernate.mapping.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Integer> {
}
