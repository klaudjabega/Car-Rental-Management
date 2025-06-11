package com.example.drivenimbus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
//    @JoinColumn(name = "carID", nullable = false)
    private Car car;

    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickupDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private Status bookingStatus;
    private Date createdAt;

    // Default constructor
    public Booking() {
    }

    // Parameterized constructor
    public Booking(Users user, Car car, Long bookingId, LocalDate pickupDate, LocalDate returnDate,
                   Status bookingStatus) {
        this.user = user;
        this.car = car;
        this.bookingId = bookingId;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.bookingStatus = bookingStatus;
        this.createdAt = new Date();
    }

    // Getters
    public Long getBookingId() {
        return bookingId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Status getBookingStatus() {
        return bookingStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setBookingId(Long bookingID) {
        this.bookingId = bookingID;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setBookingStatus(Status bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}