package com.codepresso.codepresso.repository;

import com.codepresso.codepresso.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 추가 쿼리 필요 시 정의
}
