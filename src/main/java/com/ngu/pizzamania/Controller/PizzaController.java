package com.ngu.pizzamania.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ApiResponse;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Service.PizzaService;

@RestController
@RequestMapping("/pizza")
@AllArgsConstructor
public class PizzaController {
    private final PizzaService pizzaService;
    private ObjectMapper objectMapper;

    /**
     * @param pizza
     * @return
     */
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pizza createPizza(@RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    /**
     * @param pizza
     * @return
     */
    @PutMapping("/updates/{id}")
    public Pizza updatePizza(@RequestBody Pizza pizza) {
        return pizzaService.updatePizza(pizza);
    }

    /**
     * @return all pizza
     */
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

    /**
     * @return all pizza by their category
     */
    @GetMapping("/groupByCategory")
    public Map<String, List<Pizza>> getPizzaByCategory() {
        List<Pizza> allpizzas = pizzaService.getAllPizzas();
        Map<String, List<Pizza>> groupByCategory = allpizzas.stream()
                .collect(Collectors.groupingBy(pizza -> pizza.getCategory().getName()));
        return groupByCategory;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Integer id, HttpServletRequest request) throws IOException {
        Pizza currentPizza = pizzaService.getPizzaById(id).orElseThrow(()-> new ResourceNotFoundException(
                ErrorResponse.builder()
                        .message("Pizza with id "+id+" does not exists")
                        .build()
        ));
        Pizza updatedPizza = objectMapper.readerForUpdating(currentPizza).readValue(request.getReader());
        pizzaService.updatePizza(updatedPizza);
        return new ResponseEntity<>(updatedPizza, HttpStatus.ACCEPTED);
    }
}
