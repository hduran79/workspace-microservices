package com.votre.microservices.product.entity;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_regions")
public class Region {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
}
