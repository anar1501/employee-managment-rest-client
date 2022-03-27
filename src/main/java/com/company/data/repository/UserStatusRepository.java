package com.company.data.repository;

import com.company.data.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    UserStatus findUserStatusByStatusId(Long id);
}
