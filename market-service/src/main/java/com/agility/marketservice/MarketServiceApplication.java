package com.agility.marketservice;

import com.agility.marketservice.repository.InitializeDatabase;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
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
