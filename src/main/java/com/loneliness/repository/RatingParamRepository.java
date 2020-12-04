package com.loneliness.repository;

import com.loneliness.entity.domain.RatingParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingParamRepository extends JpaRepository<RatingParam, Integer> {
    Optional<List<RatingParam>> findAllByRatedBookIsNotNull();

    Optional<List<RatingParam>> findAllByRatedBookIsNotNullAndVotedUserIsNotNull();
}
