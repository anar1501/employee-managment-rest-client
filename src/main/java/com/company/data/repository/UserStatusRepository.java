package com.company.data.repository;

import com.company.data.entity.User;
import com.company.data.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    UserStatus findUserStatusById(Long id);
}
