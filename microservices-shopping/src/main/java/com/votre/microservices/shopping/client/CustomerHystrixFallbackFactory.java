package com.votre.microservices.shopping.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.votre.microservices.shopping.model.CustomerDTO;

@Component
public class CustomerHystrixFallbackFactory implements ICustomerClient {

    @Override
    public ResponseEntity<CustomerDTO> getCustomer(long id) {
        CustomerDTO customer = CustomerDTO.builder()
                .firstName("none")
                .lastName("none")
                .email("none")
                .photoUrl("none").build();
        return ResponseEntity.ok(customer);
    }
}
