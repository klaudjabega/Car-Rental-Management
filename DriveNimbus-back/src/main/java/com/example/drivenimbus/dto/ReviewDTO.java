package com.example.drivenimbus.dto;

public class ReviewDTO {
    private String review;
    private Integer rating;

    public ReviewDTO() {
    }

    public ReviewDTO(String review, Integer rating) {
        this.review = review;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
