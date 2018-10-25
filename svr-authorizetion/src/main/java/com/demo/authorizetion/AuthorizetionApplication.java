package com.demo.authorizetion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AuthorizetionApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizetionApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthorizetionApplication.class);
	}

}
