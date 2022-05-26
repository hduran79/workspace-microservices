package com.votre.microservices.shopping.dto;

import java.util.Date;
import java.util.List;

import com.votre.microservices.shopping.entity.InvoiceItem;

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

	private List<InvoiceItem> items;

	private String state;

	private String message;

}
