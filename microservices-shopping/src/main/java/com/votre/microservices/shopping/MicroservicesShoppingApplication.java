package com.votre.microservices.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicesShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesShoppingApplication.class, args);
	}

}
