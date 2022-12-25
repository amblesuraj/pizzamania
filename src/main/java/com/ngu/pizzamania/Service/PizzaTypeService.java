package com.ngu.pizzamania.Service;


import com.ngu.pizzamania.Model.PizzaType;

import java.util.List;
import java.util.Optional;

public interface PizzaTypeService {
    PizzaType save(PizzaType pizzaType);

    Optional<PizzaType> getPizzaTypeById(Integer id);

    List<PizzaType> getAllPizzaTypes();

    void DeletePizzaTypeById(Integer id);

    boolean findByPizzaTypeName(String name);
}
