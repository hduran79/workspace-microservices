package com.votre.microservices.product.service;

import java.util.List;

import com.votre.microservices.product.dto.ProductDTO;
import com.votre.microservices.product.entity.Category;
import com.votre.microservices.product.entity.Product;

public interface IProductService {

	public List<Product> listAllProduct();

	public Product getProduct(Long id);

	public Product createProduct(ProductDTO product);

	public Product updateProduct(ProductDTO product);

	public Product deleteProduct(Long id);

	public List<Product> findByCategory(Category category);

	public Product updateStock(Long id, Double quantity);
	
	public Product getProdcutName(String name);

}
