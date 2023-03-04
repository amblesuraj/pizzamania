package com.ngu.pizzamania.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "{user.name.empty}")
    private String name;
    private boolean active;
    @Column(unique = true,nullable = false)
    @NotEmpty(message = "{user.username.empty}")
    private String username;
    @NotEmpty(message = "{user.email.empty}")
    @Column(unique = true,nullable = false)
    @Email(message = "{user.email.valid }")
    private String email;
    @Column(nullable = false)
    @NotEmpty(message = "{user.password.empty}")
    @Size(min = 8, message = "{user.password.size}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "{user.password.pattern}")
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
