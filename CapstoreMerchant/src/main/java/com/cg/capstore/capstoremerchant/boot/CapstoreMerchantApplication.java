package com.cg.capstore.capstoremerchant.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cg.capstore.capstoremerchant.controllers")
public class CapstoreMerchantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoreMerchantApplication.class, args);
	}
}
