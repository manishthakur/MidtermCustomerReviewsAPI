package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewDocumentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    @Order
    public void createReview() {
        Review review = reviewRepository.save(getReview());
        assertThat(review).isNotNull();
        assertThat(reviewRepository.findById(review.getId())).isNotEmpty();
        assertThat(reviewRepository.findByProductId(review.getProduct().getId())).isNotNull();
        assertThat(reviewRepository.findAll()).isNotNull();
    }

    private Review getReview() {
        Review review = new Review();
        review.setRating(4.5f);
        review.setText("Test Review.");
        review.setProduct(getProduct());
        return review;
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Test Product while Testing.");
        product.setPrice(101.0);
        product = productRepository.save(product);
        return productRepository.findById(product.getId()).get();
    }

}