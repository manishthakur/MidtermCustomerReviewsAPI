package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.CommentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentMongoRepository extends MongoRepository<CommentDocument, String> {
}
