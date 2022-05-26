package com.votre.microservices.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.votre.microservices.product.config.MicroServicesBusinessException;
import com.votre.microservices.product.dto.ProductDTO;
import com.votre.microservices.product.entity.Category;
import com.votre.microservices.product.entity.Product;
import com.votre.microservices.product.repository.IProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * @RequiredArgsConstructor de esta forma se inyecta la dependencia
 *                          IProductRepository al constructor
 * @author hduran
 *
 */

@Service
@RequiredArgsConstructor
public class ProdcutServiceImpl implements IProductService {

	Product productDB = null;

	private final IProductRepository productRepository;

	@Override
	public List<Product> listAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product createProduct(ProductDTO productDTO) {

		productDB = getProdcutName(productDTO.getName());

		if (null != productDB) {
			return productDB;
		}
		
		return productRepository.save(Product.builder()
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.stock(productDTO.getStock())
				.price(productDTO.getPrice())
				.status("CREATED")
				.createAt(new Date())
				.category(productDTO.getCategory())
				.build());
	}

	@Override
	public Product updateProduct(ProductDTO productDTO) {
		
		productDB = getProduct(productDTO.getId());

		if (null == productDB) {
			throw new MicroServicesBusinessException("001", "El producto no existe !!!");
		}

		return productRepository.save(Product.builder()
				.id(productDTO.getId())
				.name(productDTO.getName())
				.description(productDTO.getDescription())
				.stock(productDTO.getStock())
				.price(productDTO.getPrice())
				.status("CREATED")
				.createAt(new Date())
				.category(productDTO.getCategory())
				.build());
	}

	@Override
	public Product deleteProduct(Long id) {

		productDB = getProduct(id);

		if (null == productDB) {
			throw new MicroServicesBusinessException("001", "El producto no existe !!!");
		}

		productDB.setStatus("DELETED");
		return productRepository.save(productDB);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public Product updateStock(Long id, Double quatity) {

		productDB = getProduct(id);

		if (null == productDB) {
			throw new MicroServicesBusinessException("001", "El producto no existe !!!");
		}

		productDB.setStock(productDB.getStock() + quatity);

		return productRepository.save(productDB);
	}
	
	@Override
	public Product getProdcutName(String name) {
		return productRepository.findByName(name);
	}

}
