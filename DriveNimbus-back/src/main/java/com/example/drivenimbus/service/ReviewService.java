package com.example.drivenimbus.service;

import com.example.drivenimbus.dto.ReviewDTO;
import com.example.drivenimbus.model.Review;
import com.example.drivenimbus.repository.CarRepository;
import com.example.drivenimbus.repository.ReviewRepository;
import com.example.drivenimbus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public Review saveReview(ReviewDTO reviewDTO, Long carId, Long userId) {
        if (!carRepository.existsById(carId) || !userRepository.existsById(userId)) {
            throw new RuntimeException("Invalid car or user ID");
        }

        Review review = new Review();
        review.setReviewText(reviewDTO.getReview());
        review.setRating(reviewDTO.getRating());
        review.setCar(carRepository.findById(carId).orElse(null));
        review.setUser(userRepository.findById(userId).orElse(null));
        return reviewRepository.save(review);
    }

    public List<Review> fetchReviewsByCarId(Long carId) {
        Optional<List<Review>> reviewList = reviewRepository.findAllByCarCarId(carId);
        if (reviewList.isEmpty()) {
            throw new RuntimeException("No reviews found for this car");
        }
        return reviewList.get();
    }

    public List<Review> fetchReviewsByUserId(Long userId) {
        Optional<List<Review>> reviewList = reviewRepository.findAllByUserUserId(userId);
        if (reviewList.isEmpty()) {
            throw new RuntimeException("No reviews found for this user");
        }
        return reviewList.get();
    }

    public List<Review> fetchReviewsByCarIdAndUserId(Long carId, Long userId) {
        Optional<List<Review>> reviewList = reviewRepository.findAllByCarCarIdAndUserUserId(carId, userId);
        if (reviewList.isEmpty()) {
            throw new RuntimeException("No reviews found for this car or user");
        }
        return reviewList.get();
    }

    public List<Review> fetchAllReviews() {
        return (List<Review>) reviewRepository.findAll();
    }

    public boolean deleteReviewById(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}
