package com.sibmed;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sibmed.domain.Bula;
import com.sibmed.repositories.BulaRepository;

@SpringBootApplication
public class SibmedApplication implements CommandLineRunner {
	
	@Autowired
	private BulaRepository bulaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SibmedApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Bula b1 = new Bula(null,"nome", "fabricante", "indicacao", "contraIndicacao", "dir");
		Bula b2 = new Bula(null,"nome", "fabricante", "indicacao", "contraIndicacao", "dir");
 		bulaRepository.saveAll(Arrays.asList(b1,b2));
	}
}
