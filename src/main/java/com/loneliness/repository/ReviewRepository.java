package com.loneliness.repository;

import com.loneliness.entity.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<List<Review>> findByNewsReview_Id(Integer newsReview_id);

    Optional<List<Review>> findReviewsByIdIsNotNull();

    Optional<List<Review>> findByNewsReviewIsNotNullOrderByDataDesc();

    Optional<List<Review>> findByNewsReviewIsNotNullOrderByDataAsc();

    Optional<List<Review>> findByNewsReviewIsNotNullOrderByDataDescMarkDesc();

    Optional<List<Review>> findBySurveyedBookIsNotNullOrderByDataDesc();

    Optional<List<Review>> findBySurveyedBookIsNotNullOrderByDataAsc();

    Optional<List<Review>> findBySurveyedBookIsNotNullOrderByDataDescMarkDesc();

    Optional<List<Review>> findReviewsByNewsReviewIsNotNullOrderByDataDesc();

}
