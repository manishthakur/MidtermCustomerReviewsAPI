package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.CommentDocument;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import org.junit.jupiter.api.Test;
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
public class ReviewMongoRepositoryTests {

    @Autowired
    ReviewMongoRepository reviewMongoRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testReviewDocuments() {

        ReviewDocument reviewDocument = reviewMongoRepository.save(getReviewDocument());

        assertThat(reviewDocument).isNotNull();
        assertThat(reviewMongoRepository.findById(reviewDocument.getId())).isNotEmpty();
        assertThat(reviewMongoRepository.findByProductId(reviewDocument.getProductId())).isNotNull();
        assertThat(reviewMongoRepository.findAll()).isNotNull();

        reviewDocument.getComments().add(getCommentDocument());
        reviewDocument = reviewMongoRepository.save(reviewDocument);
        assertThat(reviewDocument).isNotNull();
    }

    @Test
    public void testReviewDocumentsWithTemplates() {

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
        reviewDocument.setProductId("1");
        return reviewDocument;
    }

    private CommentDocument getCommentDocument() {
        CommentDocument commentDocument = new CommentDocument();
        commentDocument.setText("Great Review");
        return commentDocument;
    }
}