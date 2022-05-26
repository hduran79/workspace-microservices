package com.votre.microservices.product;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.votre.microservices.product.entity.Category;
import com.votre.microservices.product.entity.Product;
import com.votre.microservices.product.repository.IProductRepository;


/**
 * @DataJpaTest para testear una clase JPA
 * @Autowired para inyectar la clase ProductRepository 
 * @author hduran
 *
 */
@DataJpaTest
public class ProductRepositoryMockTest {

	@Autowired
    private IProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct(){
        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();
        productRepository.save(product01);

        List<Product> founds= productRepository.findByCategory(product01.getCategory());

        Assertions.assertThat(founds.size()).isEqualTo(3);
    }
}
