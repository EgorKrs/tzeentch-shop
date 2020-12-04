package com.loneliness.service;

import com.loneliness.entity.domain.UserCreditDetails;
import com.loneliness.repository.UserCreditRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCreditService extends CRUDService<UserCreditDetails> {
    public UserCreditService(UserCreditRepository authorRepository, SearchService searchService) {
        repository = authorRepository;
        this.searchService = searchService;
    }
}
