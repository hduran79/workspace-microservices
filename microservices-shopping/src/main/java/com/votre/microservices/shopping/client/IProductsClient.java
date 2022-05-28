package com.votre.microservices.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.votre.microservices.shopping.dto.ProductDTO;

@RequestMapping(value = "products")
@FeignClient(name = "product-service")
public interface IProductsClient {
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id);
	
	@GetMapping(value = "/{id}/stock")
	public ResponseEntity<ProductDTO> updateStockProduct(@PathVariable Long id,
			@RequestParam(name = "quantity", required = true) Double quantity);

}
