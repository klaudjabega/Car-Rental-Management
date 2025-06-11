package com.example.drivenimbus.repository;

import com.example.drivenimbus.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findAllByCarCarId(Long carCarId);
    Optional<List<Review>> findAllByUserUserId(Long userId);
    Optional<List<Review>> findAllByCarCarIdAndUserUserId(Long carCarId, Long userId);
    boolean existsByReviewId(Long reviewId);
}
