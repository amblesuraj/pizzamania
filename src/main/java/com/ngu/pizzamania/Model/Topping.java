package com.ngu.pizzamania.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "toppings")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "topping_id", scope = Topping.class)
public class Topping implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topping_id;
    private String name;

    @ManyToMany(mappedBy = "toppings", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Pizza> pizzas = new HashSet<>();

    public void addPizzas(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    public void removePizzas(Pizza pizza) {
        this.pizzas.remove(pizza);
    }
}
