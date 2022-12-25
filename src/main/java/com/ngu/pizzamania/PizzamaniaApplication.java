package com.ngu.pizzamania;

import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Repository.RoleRepository;
import com.ngu.pizzamania.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class PizzamaniaApplication{

	public static void main(String[] args) {
		SpringApplication.run(PizzamaniaApplication.class, args);
	}
}
