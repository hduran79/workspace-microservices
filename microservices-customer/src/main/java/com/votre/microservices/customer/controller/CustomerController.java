package com.votre.microservices.customer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.votre.microservices.customer.dto.CustomerDTO;
import com.votre.microservices.customer.service.ICustomerService;
import com.votre.microservices.product.entity.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
    ICustomerService customerService;

    // -------------------Retrieve All Customers--------------------------------------------

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomers(@RequestParam(name = "regionId" , required = false) Long regionId ) {
        return customerService.listAllCustomers(regionId);
    }

    // -------------------Retrieve Single Customer------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        return customerService.getCustomer(id);
    }

    // -------------------Create a Customer-------------------------------------------

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDTO customer, BindingResult result) {

        Customer customerDB = customerService.createCustomer(customer, result);

        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    // ------------------- Update a Customer ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCustomer(
    		@PathVariable("id") long id, 
    		@RequestBody CustomerDTO customer) {
    	customer.setId(id);
        return customerService.updateCustomer(customer);
    }

    // ------------------- Delete a Customer-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomer(
    		@PathVariable("id") long id) {
        return customerService.deleteCustomer(id);
    }

    

}
