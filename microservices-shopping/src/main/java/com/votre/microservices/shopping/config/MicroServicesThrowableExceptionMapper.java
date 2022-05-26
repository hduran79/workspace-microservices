package com.votre.microservices.shopping.config;

import javax.json.Json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MicroServicesThrowableExceptionMapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(MicroServicesThrowableExceptionMapper.class);

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handle(Throwable exception) {

		LOGGER.error(exception.getMessage(), exception);

		return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
				.body(Json.createObjectBuilder().add("code", exception.getClass().getSimpleName())
						.add("message", (exception.getMessage() == null ? exception.getClass().getSimpleName()
								: exception.getMessage()))
						.build().toString());

	}

}
