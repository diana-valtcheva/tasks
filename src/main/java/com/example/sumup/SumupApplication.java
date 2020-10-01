package com.example.sumup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.sumup.*")
public class SumupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SumupApplication.class, args);
	}

}
