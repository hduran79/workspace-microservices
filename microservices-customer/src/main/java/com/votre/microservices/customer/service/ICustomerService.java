package com.votre.microservices.customer.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.votre.microservices.customer.dto.CustomerDTO;
import com.votre.microservices.product.entity.Customer;
import com.votre.microservices.product.entity.Region;

public interface ICustomerService {
	
	public ResponseEntity<List<Customer>> listAllCustomers(Long regionId);
	public List<Customer> findCustomerAll();
    public List<Customer> findCustomersByRegion(Region region);

    public Customer createCustomer(CustomerDTO customer, BindingResult result);
    public ResponseEntity<?> updateCustomer(CustomerDTO customer);
    public ResponseEntity<?> deleteCustomer(long id);
    public ResponseEntity<Customer> getCustomer(Long id);
    

}
