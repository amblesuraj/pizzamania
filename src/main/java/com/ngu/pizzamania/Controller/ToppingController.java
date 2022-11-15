package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Model.Topping;
import com.ngu.pizzamania.Service.ToppingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topping")
public class ToppingController {

    private ToppingService toppingService;

    public ToppingController(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    @PostMapping("/create")
    public Topping createTopping(@RequestBody Topping topping) {
        return toppingService.createTopping(topping);
    }

    @PutMapping("/update/{id}")
    public Topping updateTopping(@RequestBody Topping topping) {
        return toppingService.updateTopping(topping);
    }

    @GetMapping("/toppings")
    public List<Topping> getAllToppings() {
        return toppingService.allToppings();
    }

    @GetMapping("/{id}")
    public Topping getToppingById(@PathVariable int id) {
        return toppingService.getToppingById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteToppingById(@PathVariable int id) {
        toppingService.deleteToppingById(id);
        return "Topping deleted with Id " + id;
    }
}
