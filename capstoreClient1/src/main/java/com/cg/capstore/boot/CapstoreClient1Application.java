package com.cg.capstore.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cg.capstore.controller")
public class CapstoreClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(CapstoreClient1Application.class, args);
	}
}
