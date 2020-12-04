package com.loneliness.service;

import com.loneliness.entity.domain.RatingParam;
import com.loneliness.repository.RatingParamRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Configurable
@Service
public class RatingParamService extends CRUDService<RatingParam> {
    public RatingParamService(RatingParamRepository repository, SearchService searchService) {
        this.repository = repository;
        this.searchService = searchService;
    }

    public Double getAverageRating(Integer bookId) {
        Optional<List<RatingParam>> optional = ((RatingParamRepository) repository).findAllByRatedBookIsNotNull();
        optional.ifPresent(ratingParams -> ratingParams.removeIf(ratingParam -> !ratingParam.getRatedBook().getId().equals(bookId)));
        Double sum = Double.valueOf("0");
        if (optional.isPresent()) {
            for (RatingParam rating : optional.get()) {
                sum += rating.getRating();
            }
            return sum / optional.get().size();
        }
        return sum;
    }

    @Override
    public RatingParam save(RatingParam ratingParam) {

        Optional<List<RatingParam>> optional = ((RatingParamRepository) repository).findAllByRatedBookIsNotNullAndVotedUserIsNotNull();
        optional.ifPresent(ratingParams -> ratingParams.removeIf(param -> !isFound(
                param, ratingParam.getRatedBook().getId(), ratingParam.getVotedUser().getId())));
        optional.ifPresent(ratingParams -> ratingParams.get(0).setRating(ratingParam.getRating()));
        return optional.map(ratingParams -> repository.save(ratingParams.get(0))).orElseGet(() -> repository.save(ratingParam));
    }

    private boolean isFound(RatingParam ratingParam, Integer bookId, Integer userId) {
        return ratingParam.getVotedUser().getId().equals(userId) && ratingParam.getRatedBook().getId().equals(bookId);
    }
}
