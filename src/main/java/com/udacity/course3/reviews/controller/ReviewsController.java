package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.*;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewMongoRepository reviewMongoRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody @Valid Review review) {

        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        review.setProduct(productOptional.get());
        review = reviewRepository.save(review);

        if (review == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        ReviewDocument reviewDocument = copy(review);
        if (reviewDocument != null) {
            reviewMongoRepository.save(reviewDocument);
        }
        return new ResponseEntity<>(reviewDocument, HttpStatus.OK);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {

        List<Review> reviews = reviewRepository.findByProductId(productId);
        if (reviews == null || reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ReviewDocument> reviewDocuments = new ArrayList<>();
        reviews.forEach(review -> {
            Optional<ReviewDocument> reviewDocument = reviewMongoRepository.findById(review.getId().toString());
            if (reviewDocument.isPresent()) {
                reviewDocuments.add(reviewDocument.get());
            }
        });
        return new ResponseEntity<>(reviewDocuments, HttpStatus.OK);
    }

    private ReviewDocument copy(Review review) {

        if (review == null) {
            return null;
        }
        ReviewDocument reviewDocument = new ReviewDocument();
        reviewDocument.setId(review.getId().toString());
        reviewDocument.setProductId(review.getProduct().getId().toString());
        reviewDocument.setText(review.getText());
        reviewDocument.setRating(review.getRating());

        if (review.getComments() != null) {
            review.getComments().forEach(comment -> {
                CommentDocument commentDocument = copy(comment);
                if (commentDocument != null) {
                    reviewDocument.getComments().add(commentDocument);
                }
            });
        }
        return reviewDocument;
    }

    private CommentDocument copy(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDocument commentDocument = new CommentDocument();
        commentDocument.setId(comment.getId().toString());
        commentDocument.setText(comment.getText());
        return commentDocument;
    }
}