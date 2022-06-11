package com.votre.microservices.shopping.model;

import lombok.Data;

@Data
public class CustomerDTO {

	private Long id;
	private String numberID;
	private String firstName;
	private String lastName;
	private String email;
	private String photoUrl;
	private RegionDTO region;
	private String state;
}
