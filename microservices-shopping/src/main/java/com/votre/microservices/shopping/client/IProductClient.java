package com.votre.microservices.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.votre.microservices.shopping.model.ProductDTO;

@FeignClient(name = "product-services", path = "products")
public interface IProductClient {

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id);

	@GetMapping(value = "/{id}/stock", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductDTO> updateStockProduct(@PathVariable Long id,
			@RequestParam(name = "quantity", required = true) Double quantity);
}
