package com.sibmed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SibmedApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SibmedApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
