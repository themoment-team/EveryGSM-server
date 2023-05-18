package com.themoment.everygsm.domain.user.repository;

import com.themoment.everygsm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserEmail(String userEmail);
}
