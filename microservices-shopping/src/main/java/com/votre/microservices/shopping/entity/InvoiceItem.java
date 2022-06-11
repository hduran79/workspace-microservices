package com.votre.microservices.shopping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Positive;

import com.votre.microservices.shopping.model.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tbl_invoce_items")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Positive(message = "El stock debe ser mayor que cero")
	private Double quantity;

	private Double price;

	@Column(name = "product_id")
	private Long productId;

	@Transient
	private Double subTotal;

	@Transient
	private ProductDTO product;

}
