package com.agility.marketservice;

import com.agility.marketservice.model.Role;
import com.agility.marketservice.model.User;
import com.agility.marketservice.model.UserRoles;
import com.agility.marketservice.repository.IRoleRepository;
import com.agility.marketservice.repository.IUserRepository;
import com.agility.marketservice.repository.InitializeDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarketServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketServiceApplication.class, args);
	}

	/**
	 * Handle init database
	 */
	@Bean
	CommandLineRunner init(InitializeDatabase initializeDatabase) {
		return args -> {
			initializeDatabase.initRoles();
			initializeDatabase.initUsers();
			initializeDatabase.initCategories();
			initializeDatabase.initShippingService();
		};
	}
}
