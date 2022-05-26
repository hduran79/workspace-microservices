package com.votre.microservices.product;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.votre.microservices.product.entity.Category;
import com.votre.microservices.product.entity.Product;
import com.votre.microservices.product.repository.IProductRepository;
import com.votre.microservices.product.service.IProductService;
import com.votre.microservices.product.service.ProdcutServiceImpl;

@SpringBootTest
public class ProductServiceMockTest {

	@Mock
	private IProductRepository productRepository;

	private IProductService productService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		productService = new ProdcutServiceImpl(productRepository);
		Product computer = Product.builder()
				.id(1L)
				.name("computer")
				.category(Category.builder().id(1L).build())
				.price(Double.parseDouble("12.5"))
				.stock(Double.parseDouble("5"))
				.build();

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));
		Mockito.when(productRepository.save(computer)).thenReturn(computer);

	}

	@Test
	void whenValidGetID_ThenReturnProduct() {
		Product found = productService.getProduct(1L);
		Assertions.assertThat(found.getName()).isEqualTo("computer");

	}

	@Test
	void whenValidUpdateStock_ThenReturnNewStock() {
		Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
		Assertions.assertThat(newStock.getStock()).isEqualTo(13);
	}
}