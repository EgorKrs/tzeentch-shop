package com.loneliness.repository;

import com.loneliness.entity.domain.UserCreditDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCreditRepository extends JpaRepository<UserCreditDetails, Integer> {

}
