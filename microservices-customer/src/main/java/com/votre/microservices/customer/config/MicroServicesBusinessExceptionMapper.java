package com.votre.microservices.customer.config;

import javax.json.Json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MicroServicesBusinessExceptionMapper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServicesBusinessExceptionMapper.class);

	@ExceptionHandler(MicroServicesBusinessException.class)
	public ResponseEntity<String> handle(MicroServicesBusinessException exception) {

		LOGGER.error(exception.getCode() + " " + exception.getMessage(), exception);

		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Json.createObjectBuilder()
				.add("code", exception.getCode())
				.add("message", exception.getMessage())
				.build().toString());

	}

}
