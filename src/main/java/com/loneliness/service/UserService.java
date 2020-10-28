package com.loneliness.service;

import com.loneliness.entity.domain.Author;
import com.loneliness.entity.domain.User;
import com.loneliness.repository.AuthorRepository;
import com.loneliness.repository.UserRepository;
import com.loneliness.validate_data.Exist;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends CRUDService<User> {

    public UserService(UserRepository authorRepository, SearchService searchService){
        repository = authorRepository;
        this.searchService = searchService;
    }
    public Optional<User> findUserByGoogleId(@Length(max = 255, groups = { Exist.class}) String googleId){
        return ((UserRepository)repository).findUserByGoogleId(googleId);
    }

}
