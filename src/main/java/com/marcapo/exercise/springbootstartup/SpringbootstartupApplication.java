package com.marcapo.exercise.springbootstartup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootstartupApplication implements CommandLineRunner{

	public static void main(final String[] args) {
		SpringApplication.run(SpringbootstartupApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Started...");
	}

}
