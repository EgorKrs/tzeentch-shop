package com.loneliness.service;

import com.loneliness.entity.domain.User;
import com.loneliness.repository.UserRepository;
import com.loneliness.validate_data.Exist;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends CRUDService<User> {

    public UserService(UserRepository authorRepository, SearchService searchService) {
        repository = authorRepository;
        this.searchService = searchService;
    }

    public Optional<User> findUserByGoogleId(@Length(max = 255, groups = {Exist.class}) String googleId) {
        return ((UserRepository) repository).findUserByGoogleId(googleId);
    }

    public Optional<User> findUserByName(@Length(max = 255, groups = {Exist.class}) String name) {
        return ((UserRepository) repository).findUserByName(name);
    }

    public Optional<List<User>> findUsersThatByeBook(Integer bookId) {
        Optional<List<User>> usersByBooksIsNotNull = ((UserRepository) repository).findUsersByBooksIsNotNull();
        usersByBooksIsNotNull.ifPresent(users -> users.removeIf(user -> user.getBooks().stream().noneMatch(book -> book.getId().equals(bookId))));
        usersByBooksIsNotNull.ifPresent(users -> users.forEach(this::clearProtectedData));
        return usersByBooksIsNotNull;

    }

    private void clearProtectedData(User user) {
        user.setActivationCode("");
        user.setEmail("");
    }


}
