package com.ngu.pizzamania.Service;

import com.ngu.pizzamania.Model.Topping;

import java.util.List;

public interface ToppingService {

    Topping createTopping(Topping topping);

    Topping updateTopping(Topping topping);

    List<Topping> allToppings();

    Topping getToppingById(int id);

    void deleteToppingById(int id);
}
