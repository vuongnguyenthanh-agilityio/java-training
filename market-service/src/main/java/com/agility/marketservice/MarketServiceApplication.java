package com.agility.marketservice;

import com.agility.marketservice.model.Role;
import com.agility.marketservice.model.User;
import com.agility.marketservice.model.UserRoles;
import com.agility.marketservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarketServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			User user = new User();
			Role role = new Role();
			System.out.println("CommandLineRunner VUONG");

			userRepository.save(
					user
							.setEmail("vuong@gmail.com")
							.setFullName("Vuong")
//							.setRole(role.setCode(UserRoles.ADMIN).setName("Admin"))
			);
		};
	}
}
