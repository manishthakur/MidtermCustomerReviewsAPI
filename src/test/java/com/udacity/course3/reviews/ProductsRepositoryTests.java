package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductsRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    @Order
    public void createProduct() {
        Product product = productRepository.save(getProduct());
        assertThat(product).isNotNull();
        assertThat(productRepository.findById(product.getId())).isNotNull();
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Test Product while Testing.");
        product.setPrice(101.0);
        return product;
    }
}