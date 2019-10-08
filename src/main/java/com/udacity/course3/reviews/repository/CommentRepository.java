package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Query("select c from Comment c where c.review.id =:reviewId")
    List<Comment> findByReviewId(@Param("reviewId") Integer reviewId);

}
