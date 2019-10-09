package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ReviewDocumentRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;


    @Test
    public void contextLoads() {
    }

    @Test
    public void createReviewDocuments(@Autowired MongoTemplate mongoTemplate) {

        ReviewDocument reviewDocument = mongoTemplate.save(getReviewDocument(), "review");
        assertThat(reviewDocument).isNotNull();
        assertThat(mongoTemplate.findById(reviewDocument.getId(), ReviewDocument.class)).isNotNull();

        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(reviewDocument.getProductId()));
        assertThat(mongoTemplate.find(query, ReviewDocument.class)).isNotNull();

        assertThat(mongoTemplate.findAll(ReviewDocument.class, "review")).isNotNull();
    }

    private ReviewDocument getReviewDocument() {
        ReviewDocument reviewDocument = new ReviewDocument();
        reviewDocument.setRating(4.5f);
        reviewDocument.setText("Test Review.");
        reviewDocument.setProductId(getProduct().getId().toString());
        return reviewDocument;
    }

    private Product getProduct() {
        Product product;
        Iterable<Product> productIterable = productRepository.findAll();
        product = (productIterable != null && productIterable.iterator() != null)
                ? productIterable.iterator().next() : null;

        if (product != null) {
            return product;
        } else {
            product = new Product();
        }

        product.setName("Test Product while Testing.");
        product.setPrice(101.0);
        product = productRepository.save(product);
        return productRepository.findById(product.getId()).get();
    }
}