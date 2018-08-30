package com.cg.JspTest1.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.cg.JspTest1.bean")
@ComponentScan("com.cg.JspTest1")
@EnableJpaRepositories("com.cg.JspTest1.repo")
public class JspTest1Application {

	public static void main(String[] args) {
		SpringApplication.run(JspTest1Application.class, args);
	}
}
