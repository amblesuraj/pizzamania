package com.ngu.pizzamania.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Model.Topping;
import com.ngu.pizzamania.Repository.PizzaRepository;
import com.ngu.pizzamania.Repository.ToppingRepository;
import com.ngu.pizzamania.Service.PizzaService;

@Service
@Transactional
public class PizzaServiceImpl implements PizzaService {

    private PizzaRepository pizzaRepository;
    private ToppingRepository toppingRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository, ToppingRepository toppingRepository) {
        this.pizzaRepository = pizzaRepository;
        this.toppingRepository = toppingRepository;
    }

    @Override
    public Pizza createPizza(Pizza pizza) {
        Pizza newPizza = new Pizza();
        newPizza.setPizza_id(pizza.getPizza_id());
        newPizza.setName(pizza.getName());
        newPizza.setSize(pizza.getSize());
        newPizza.setType(pizza.getType());
        newPizza.setAvailable(pizza.isAvailable());
        newPizza.setCategory(pizza.getCategory());
        newPizza.setPizzaType(pizza.getPizzaType());
        newPizza.getToppings().addAll(pizza.getToppings().stream()
                .map(topping -> {
                    Topping topping1 = topping;
                    if (topping1.getTopping_id() > 0) {
                        topping1 = toppingRepository.getReferenceById(topping.getTopping_id());
                        // topping1.getPizzas().add(newPizza);
                        topping1.addPizzas(newPizza);
                    }
                    return topping1;
                }).collect(Collectors.toSet()));
        return pizzaRepository.save(newPizza);
    }

    @Override
    public Pizza updatePizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza findPizzaById(int id) {
        return pizzaRepository.getReferenceById(id);
    }

    @Override
    public void deletePizzaById(int id) {
        Pizza pizza = pizzaRepository.findById(id).get();
        for (Topping topping : pizza.getToppings()) {
            // topping.getPizzas().remove(pizza);
            topping.removePizzas(pizza);
        }

        // pizza.getToppings().removeAll(pizza.getToppings().stream()
        // .map(topping -> {
        // Topping topping1 = topping;
        // if(topping1.getTopping_id() > 0){
        // topping1 = toppingRepository.getOne(topping.getTopping_id());
        // topping1.getPizzas().remove(pizza);
        // }
        // return topping1;
        // }).collect(Collectors.toSet()));
        pizzaRepository.deleteById(id);
    }
}
