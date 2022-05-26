package com.votre.microservices.product.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Si el nombre de la tabla es diferente al nombre de la clase la decoramos con @table
 * @GeneratedValue para indicar que el campo Id es incremental
 * @Data: Genera los setters y getters. 
 * @AllArgsConstructor: Genera un constructor con todos los argumentos  
 * @NoArgsConstructor: Genera un constructor vacio. 
 * Si el nombre del atrinbuto es diferente en la tabla se agrega el decorador @Column(name = "create_at") 
 * Para los campos de tipo fecha se agrega el decorador @Temporal(TemporalType.TIMESTAMP) y el tipo de fecha  
 * @ManyToOne(fetch = FetchType.LAZY) Se carga de manera peresoza, es decir carga los valores de la tabla categorias solo cuando se requiera */

@Table(name = "tbl_products") 
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	private String name;
	
	private String description;
		
	private Double stock;
	
	private Double price;
	
	private String status;

	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Category category;
}
