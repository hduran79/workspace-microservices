package com.votre.microservices.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/customers")
@FeignClient(name = "customer-services")
public interface ICustomerClient {
	
	@GetMapping(value = "/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") long id);

}
