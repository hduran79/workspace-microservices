package com.votre.microservices.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votre.microservices.product.entity.Customer;
import com.votre.microservices.product.entity.Region;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
	
	public Customer findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);

}
