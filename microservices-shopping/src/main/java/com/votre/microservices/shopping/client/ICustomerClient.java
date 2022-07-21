package com.votre.microservices.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.votre.microservices.shopping.model.CustomerDTO;

@FeignClient(name = "customer-services", fallback = CustomerHystrixFallbackFactory.class)
public interface ICustomerClient {

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") long id);

}
