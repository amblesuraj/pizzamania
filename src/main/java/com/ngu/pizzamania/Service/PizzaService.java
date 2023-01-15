package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Model.Pizza;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PizzaService {
    Pizza createPizza(Pizza pizza);

    Pizza updatePizza(Pizza pizza);

    List<Pizza> getAllPizzas();

    Pizza findPizzaById(int id);

    void deletePizzaById(int id);

    Optional<Pizza> getPizzaById(Integer id);
}
