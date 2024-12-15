package com.wizh.SalesApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SalesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesAppApplication.class, args);
	}

}
