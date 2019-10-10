package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    private ReviewMongoRepository documentRepository;

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
        return new ResponseEntity<>(review, HttpStatus.OK);
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
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    /**
     * Creates a reviewDocument document for a product.
     * <p>
     * 1. Add argument for reviewDocument document. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save reviewDocument.
     *
     * @param productId The id of the product.
     * @return The created reviewDocument or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviewdocs/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewDocumentForProduct(@PathVariable("productId") Integer productId, @RequestBody @Valid ReviewDocument reviewDocument) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        reviewDocument.setProductId(productOptional.get().getId().toString());
        reviewDocument = documentRepository.save(reviewDocument);
        if (reviewDocument == null) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(reviewDocument, HttpStatus.OK);
    }

    /**
     * Lists reviews document by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviewdocs/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsDocumentForProduct(@PathVariable("productId") @NotNull Integer productId) {
        List<ReviewDocument> reviewDocuments = documentRepository.findByProductId(productId.toString());
        if (reviewDocuments == null || reviewDocuments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviewDocuments, HttpStatus.OK);
    }
}