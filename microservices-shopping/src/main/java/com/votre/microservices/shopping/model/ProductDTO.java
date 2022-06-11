package com.votre.microservices.shopping.model;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDTO {

	private Long id;
	private String name;
	private String description;
	private Double stock;
	private Double price;
	private String status;
	private Date createAt;
	private CategoryDTO category;

}
