package com.votre.microservices.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votre.microservices.product.entity.Category;
import com.votre.microservices.product.entity.Product;

/**
 * @Repository: un mecanismo para encapsular el comportamiento de
 *              almacenamiento, recuperación y búsqueda que emula una colección
 *              de objetos JpaRepository mapea la entidad Product
 */

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findByCategory(Category category);
	public Product findByName(String name);

}
