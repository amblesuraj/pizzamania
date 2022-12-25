package com.ngu.pizzamania.Repository;

import com.ngu.pizzamania.Model.PizzaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaTypeRepository extends JpaRepository<PizzaType, Integer> {
    boolean existsByName(String name);
}
