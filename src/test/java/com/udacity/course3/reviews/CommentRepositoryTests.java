package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTests {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createComment() {
        Comment comment = commentRepository.save(getComment());

        assertThat(comment).isNotNull();
        assertThat(commentRepository.findById(comment.getId())).isNotNull();
        assertThat(commentRepository.findByReviewId(comment.getReview().getId())).isNotNull();
        assertThat(commentRepository.findAll()).isNotNull();
    }

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setText("Test Comment.");
        comment.setReview(getReview());
        return comment;
    }

    private Review getReview() {
        Review review = new Review();
        review.setRating(4.5f);
        review.setText("Test Review");
        review.setProduct(getProduct());
        review = reviewRepository.save(review);
        return reviewRepository.findById(review.getId()).get();
    }

    private Product getProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(101.0);
        product = productRepository.save(product);
        return productRepository.findById(product.getId()).get();
    }
}