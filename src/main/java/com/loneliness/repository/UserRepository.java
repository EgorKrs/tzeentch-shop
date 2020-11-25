package com.loneliness.repository;

import com.loneliness.entity.domain.User;
import com.loneliness.validate_data.Exist;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
     Optional<User> findUserByGoogleId(@Length(max = 255, groups = { Exist.class}) String googleId);
     Optional<User> findUserByName(@Length(max = 255, groups = { Exist.class}) String name);
}
