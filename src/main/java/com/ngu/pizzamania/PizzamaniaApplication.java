package com.ngu.pizzamania;

import com.ngu.pizzamania.Model.Role;
import com.ngu.pizzamania.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PizzamaniaApplication implements CommandLineRunner{

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(PizzamaniaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createRoleIfNotExists("ROLE_SUPERADMIN");
		createRoleIfNotExists("ROLE_ADMIN");
		createRoleIfNotExists("ROLE_USER");
	}


	@Transactional
	public void createRoleIfNotExists(String name){
		Role role = roleRepository.findByName(name);
		if(role != null){
			return;
		}
		role = new Role(name);
		roleRepository.save(role);
	}
}
