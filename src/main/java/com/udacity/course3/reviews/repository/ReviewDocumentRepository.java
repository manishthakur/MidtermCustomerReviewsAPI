package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewDocumentRepository extends MongoRepository<ReviewDocument, String> {

    List<ReviewDocument> findByProductId(String productId);

}
