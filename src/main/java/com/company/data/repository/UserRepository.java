package com.company.data.repository;

import com.company.data.entity.User;
import com.company.data.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    User findByActivationCode(String activationCode);

    Optional<User> findBySixDigitCode(String sixDigitCode);

    UserStatus findByStatusId(Long status_id);

    Optional<User>findUserByStatusId(Long status_id);

}
