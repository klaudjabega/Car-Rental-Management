package com.example.drivenimbus.repository;

import com.example.drivenimbus.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByemail(String email);
    Optional<Users> findByVerificationToken(String username);
    boolean existsByEmail(String email);
}
