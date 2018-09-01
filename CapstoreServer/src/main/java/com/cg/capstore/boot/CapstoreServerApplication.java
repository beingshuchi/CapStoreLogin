package com.cg.capstore.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.cg.capstore")
@EnableJpaRepositories("com.cg.capstore.repo")
@EntityScan("com.cg.capstore.beans")
public class CapstoreServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoreServerApplication.class, args);
	}
}
