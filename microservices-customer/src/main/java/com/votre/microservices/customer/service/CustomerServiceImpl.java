package com.votre.microservices.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votre.microservices.customer.config.ErrorMessage;
import com.votre.microservices.customer.dto.CustomerDTO;
import com.votre.microservices.customer.repository.ICustomerRepository;
import com.votre.microservices.product.entity.Customer;
import com.votre.microservices.product.entity.Region;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
    ICustomerRepository customerRepository;
	
	@Override
	public ResponseEntity<List<Customer>> listAllCustomers(Long regionId) {
		List<Customer> customers =  new ArrayList<>();
		
        if (null ==  regionId) {
            customers = findCustomerAll();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{
            Region Region= new Region();
            Region.setId(regionId);
            customers = findCustomersByRegion(Region);
            if ( null == customers ) {
                log.error("Customers with Region id {} not found.", regionId);
                return  ResponseEntity.notFound().build();
            }
        }

        return  ResponseEntity.ok(customers);
	}

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(CustomerDTO pCustomer, BindingResult result) {
    	
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        
        Customer customer = Customer.builder()
            	.id(pCustomer.getId())
            	.email(pCustomer.getEmail())
            	.firstName(pCustomer.getFirstName())
            	.numberID(pCustomer.getNumberID())
            	.lastName(pCustomer.getLastName())
            	.photoUrl(pCustomer.getPhotoUrl())
            	.state(pCustomer.getState())
            	.region(pCustomer.getRegion())
            	.build();
        
        log.info("Creating Customer : {}", customer);

        Customer customerDB = customerRepository.findByNumberID( customer.getNumberID() );
        if (customerDB != null){
            return  customerDB;
        }

        customer.setState("CREATED");
        customerDB = customerRepository.save ( customer );
        return customerDB;
    }

    @Override
    public ResponseEntity<?> updateCustomer(CustomerDTO pCustomer) {
    	
    	 Customer customer = Customer.builder()
             	.id(pCustomer.getId())
             	.email(pCustomer.getEmail())
             	.firstName(pCustomer.getFirstName())
             	.numberID(pCustomer.getNumberID())
             	.lastName(pCustomer.getLastName())
             	.photoUrl(pCustomer.getPhotoUrl())
             	.state(pCustomer.getState())
             	.region(pCustomer.getRegion())
             	.build();
    	
    	long id = customer.getId();
    	
    	log.info("Updating Customer with id {}", id);
    	
        Customer customerDB = getCustomerLocal(id);
        
        if (customerDB == null){
        	 log.error("Unable to update. Customer with id {} not found.", id);
             return  ResponseEntity.notFound().build();
        }
        
        customerDB.setId(customerDB.getId());
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        
        Customer currentCustomer = customerRepository.save(customerDB);

        return ResponseEntity.ok(currentCustomer);
    }

    @Override
    public ResponseEntity<?> deleteCustomer(long id) {
    	
    	log.info("Fetching & Deleting Customer with id {}", id);

        Customer customerDB = getCustomerLocal(id);
        
        if (customerDB ==null){
        	log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        
        customerDB.setState("DELETED");
        
        Customer customerSave = customerRepository.save(customerDB);
        return ResponseEntity.ok(customerSave);
    }
    
    public Customer getCustomerLocal(Long id) {
        return  customerRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<Customer> getCustomer(Long id) {
        
        log.info("Fetching Customer with id {}", id);
        Customer customer =  getCustomerLocal(id);
        
        if (  null == customer) {
            log.error("Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customer);
    }
    
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

	

}
