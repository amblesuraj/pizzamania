package com.ngu.pizzamania.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pizzaType_id", scope = PizzaType.class)
@Table(name = "pizzaType")
@Entity
public class PizzaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pizzaType_id;

    @Column(nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "pizzaType", orphanRemoval = true)
    @JsonIgnore
    private Set<Pizza> pizzas = new HashSet<>();
}
