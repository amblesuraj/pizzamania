package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ApiResponse;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.PizzaType;
import com.ngu.pizzamania.Service.PizzaTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("pizzaType")
@Slf4j
public class PizzaTypeController {

   private final PizzaTypeService pizzaTypeService;

    @PostMapping("/save")
    public ResponseEntity<Object> savePizzaType(@RequestBody PizzaType pizzaType) {
        boolean existsPizzaType = pizzaTypeService.findByPizzaTypeName(pizzaType.getName());
        if (existsPizzaType) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .message("Pizza Type is already Exists")
                            .httpStatus(HttpStatus.CONFLICT)
                            .statusCode(HttpStatus.CONFLICT.value())
                            .timeStamp(new Date()).build()
            );
        }

        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .data(pizzaTypeService.save(pizzaType))
                        .message("pizzaType with name " + pizzaType.getName() + " Saved Successfully")
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(new Date()).build()
        );
    }

    @GetMapping(value = "all")
    public ResponseEntity<Object> getAllPizzaTypes() {
        List<PizzaType> pizzaTypes = pizzaTypeService.getAllPizzaTypes();
        if (pizzaTypes.isEmpty()) {

            throw new ResourceNotFoundException(ErrorResponse.builder().message("PizzaType using controller List does not exists").build());
//            return ResponseEntity.badRequest().body(
//                    ApiResponse.builder()
//                            .message("No PizzaTypes found")
//                            .httpStatus(HttpStatus.NOT_FOUND)
//                            .statusCode(HttpStatus.NOT_FOUND.value())
//                            .timeStamp(new Date()).build());
        }
        return ResponseEntity.ok().body(
                ApiResponse.builder()
                        .data(pizzaTypes)
                        .httpStatus(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .timeStamp(new Date()).build()
        );
    }

    @GetMapping(value = "{id}")
    public PizzaType getPizzaTypeById(@PathVariable Integer id) {
        return pizzaTypeService.getPizzaTypeById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(ErrorResponse.builder().message("PizzaType with id " + id + " does not exists").build());
        });
    }

    @GetMapping(value = "res/{id}")
    public ResponseEntity<Object> getPizzaTypeById1(@PathVariable Integer id) {

        Optional<PizzaType> OptionalPizzaTypeId = pizzaTypeService.getPizzaTypeById(id);
        if (OptionalPizzaTypeId.isEmpty()) {
            throw new ResourceNotFoundException(ErrorResponse.builder().message("PizzaType with id " + id + " does not exists").build());
        }

        return ResponseEntity.ok().body(ApiResponse.builder()
                .data(OptionalPizzaTypeId.get())
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .timeStamp(new Date())
                .build());
    }

}
