package com.votre.microservices.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDTO {

	private Long id;
	private Double quantity;
	private Double price;
	private Long productId;
	private Double subTotal;
	private ProductDTO product;

}
