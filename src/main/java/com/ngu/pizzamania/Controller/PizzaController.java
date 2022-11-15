package com.ngu.pizzamania.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Service.PizzaService;

@RestController
@RequestMapping("/pizza")
public class PizzaController {
    private PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pizza createPizza(@RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PutMapping("/update/{id}")
    public Pizza updatePizza(@RequestBody Pizza pizza) {
        return pizzaService.updatePizza(pizza);
    }

    @GetMapping("/pizzas")
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @GetMapping("/{id}")
    public Pizza findPizzaById(@PathVariable int id) {
        return pizzaService.findPizzaById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePizzaById(@PathVariable int id) {

        pizzaService.deletePizzaById(id);

        return "Pizza deleted with Id " + id;
    }

    @GetMapping("/groupByCategory")
    public Map<String, List<Pizza>> getPizzaByCategory() {
        List<Pizza> allpizzas = pizzaService.getAllPizzas();
        Map<String, List<Pizza>> groupByCategory = allpizzas.stream()
                .collect(Collectors.groupingBy(Pizza::getCategoryName));
        return groupByCategory;
    }
}
