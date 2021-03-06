package com.votre.microservices.shopping.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {

	private Long id;

	private String numberInvoice;

	private String description;

	private Long customerId;

	private Date createAt;

	private List<InvoiceItemDTO> items;

	private String state;

	private String message;
	
	private CustomerDTO customer;

}
