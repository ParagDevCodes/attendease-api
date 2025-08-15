package com.attendease.attendease_api.repository;

import com.attendease.attendease_api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,String> {
    Optional<Users> findByUserName(String username);
}
