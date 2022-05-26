package com.votre.microservices.product.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.votre.microservices.product.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private Long id;
	private String message;

	@NotEmpty(message = "El nombre no debe ser vacío")
	private String name;
	
	private String description;
	
	@Positive(message = "El stock debe ser mayor que cero")	
	private Double stock;
	
	private Double price;
	
	private String status;
	
	private Date createAt;
	
	@NotNull(message = "La categoría no puede ser nula !!!")
	private Category category;

}
