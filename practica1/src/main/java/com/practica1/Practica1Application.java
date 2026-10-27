package com.practica1;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.practica1.model.Product;
import com.practica1.repository.ProductRepo;

@SpringBootApplication
public class Practica1Application {
	
	private static final Logger log = LoggerFactory.getLogger(Practica1Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Practica1Application.class, args);
	}
	
	@Bean
	CommandLineRunner init(ProductRepo productRepo) {
		return args -> {
			productRepo.save(new Product ("Tablet", 199.99));
			productRepo.save(new Product ("Televisión", 250));
	        log.info("Productos de ejemplo cargados: {}", productRepo.count());
		};
	}

}
