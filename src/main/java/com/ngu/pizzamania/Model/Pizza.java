package com.ngu.pizzamania.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pizza")
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pizza_id", scope = Pizza.class)
public class Pizza implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pizza_id;
    private String name;
    private String type;
    private boolean available = true;
    private String size;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "pizza_topping", joinColumns = @JoinColumn(name = "pizza_id"), inverseJoinColumns = @JoinColumn(name = "topping_id"))
    private Set<Topping> toppings = new HashSet<>();;

    public String getCategoryName() {
        return category.getName();
    }

    public void addToppings(Topping topping) {
        this.toppings.add(topping);
    }

    public void removeToppings(Topping topping) {
        this.toppings.remove(topping);
    }

}
