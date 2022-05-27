package com.votre.microservices.config.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @EnableConfigServer Anotación para indicar que es un proyecto de configuraciones.
 * @author hduran
 *
 */

@SpringBootApplication
@EnableConfigServer
public class MicroservicesConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicesConfigServiceApplication.class, args);
	}

}
