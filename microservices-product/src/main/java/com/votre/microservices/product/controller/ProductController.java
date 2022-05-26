package com.votre.microservices.product.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.votre.microservices.product.dto.ProductDTO;
import com.votre.microservices.product.entity.Product;
import com.votre.microservices.product.service.IProductService;

@RestController
@RequestMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

	private final AtomicLong counter = new AtomicLong();
	private static final String template = "Hello %s";

	@Autowired
	private IProductService service;

	@GetMapping(value = "/test")
	public ProductDTO prodcut(@RequestParam(value = "name", defaultValue = "World") String name) {
		return ProductDTO.builder().id(counter.incrementAndGet()).message(String.format(template, name)).build();
	}

	@GetMapping(value = "/")
	public ResponseEntity<List<Product>> listProduct() {
		return ResponseEntity.ok(service.listAllProduct());
	}

	/**
	 * Servicio encargardo de consultar los productos por id
	 * 
	 * @param id id producto
	 * @return producto.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getProduct(id));
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO product) {
		return ResponseEntity.ok(service.createProduct(product));
	}

	@PutMapping
	public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO product) {
		return ResponseEntity.ok(service.updateProduct(product));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.deleteProduct(id));
	}

	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<Product> updateStockProduct(@PathVariable Long id,
			@RequestParam(name = "quantity", required = true) Double quantity) {
		return ResponseEntity.ok(service.updateStock(id, quantity));
	}

}
