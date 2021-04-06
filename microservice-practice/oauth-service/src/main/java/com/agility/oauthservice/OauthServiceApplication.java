package com.agility.oauthservice;

import com.agility.oauthservice.repository.InitializeDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class OauthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthServiceApplication.class, args);
	}

	/**
	 * Handle init database
	 */
	@Bean
	CommandLineRunner init(InitializeDatabase initializeDatabase) {
		return args -> {
			initializeDatabase.initRoles();
			initializeDatabase.initUsers();
		};
	}

}
