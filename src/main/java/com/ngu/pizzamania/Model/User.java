package com.ngu.pizzamania.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name = "User")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String password;


    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();
    public void addRole(Role role){
        this.roles.add(role);
    }
    public void removeRole(Role role){
        this.roles.remove(role);
    }
}
