package com.loneliness.repository;

import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<List<Review>> findByNewsReview_Id(Integer newsReview_id);
//    Optional<List<Review>> findByNewsReview_IdOrderByDataDesc(News newsReview_id);
//    Optional<List<Review>> findByNewsReview_IdOrderByDataAsc(News newsReview_id);
//    Optional<List<Review>> findByNewsReview_IdOrderByDataDescMarkDesc(News newsReview_id);
//    Optional<List<Review>> findByNewsReview_IdOrderByDataDesc(Integer newsReview_id);
//    Optional<List<Review>> findByNewsReview_IdOrderByDataAsc(Integer newsReview_id);
//    Optional<List<Review>> findByNewsReview_IdOrderByDataDescMarkDesc(Integer newsReview_id);
Optional<List<Review>> findReviewsByIdIsNotNull();
    Optional<List<Review>> findByNewsReviewIsNotNullOrderByDataDesc();
    Optional<List<Review>> findByNewsReviewIsNotNullOrderByDataAsc();
    Optional<List<Review>> findByNewsReviewIsNotNullOrderByDataDescMarkDesc();
    Optional<List<Review>> findReviewsByNewsReviewIsNotNullOrderByDataDesc();

}
