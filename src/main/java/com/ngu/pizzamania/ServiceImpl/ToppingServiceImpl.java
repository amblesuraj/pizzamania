package com.ngu.pizzamania.ServiceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Model.Topping;
import com.ngu.pizzamania.Repository.PizzaRepository;
import com.ngu.pizzamania.Repository.ToppingRepository;
import com.ngu.pizzamania.Service.ToppingService;

@Service
@Transactional
public class ToppingServiceImpl implements ToppingService {

    private ToppingRepository toppingRepository;

    public ToppingServiceImpl(ToppingRepository toppingRepository, PizzaRepository pizzaRepository) {
        this.toppingRepository = toppingRepository;
    }

    @Override
    public Topping createTopping(Topping topping) {
        for (Pizza pizza : topping.getPizzas()) {
            // pizza.getToppings().add(topping);
            pizza.addToppings(topping);
        }
        return toppingRepository.save(topping);
    }

    @Override
    public Topping updateTopping(Topping topping) {
        return toppingRepository.save(topping);
    }

    @Override
    public List<Topping> allToppings() {
        return toppingRepository.findAll();
    }

    @Override
    public Topping getToppingById(int id) {
        return toppingRepository.getReferenceById(id);
    }

    @Override
    public void deleteToppingById(int id) {
        Topping topping = toppingRepository.findById(id).get();

        for (Pizza pizza : topping.getPizzas()) {
            // pizza.getToppings().remove(topping);
            pizza.removeToppings(topping);
        }

        toppingRepository.deleteById(id);
    }
}
