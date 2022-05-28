package com.votre.microservices.shopping.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
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
