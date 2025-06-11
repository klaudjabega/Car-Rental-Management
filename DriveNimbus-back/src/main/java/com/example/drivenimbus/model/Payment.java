package com.example.drivenimbus.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @OneToOne
//    @JoinColumn(name = "bookingID", nullable = false)
    private Booking booking;

    private double amount;

    @Enumerated(EnumType.STRING)
    private Method paymentMethod;
    @Enumerated(EnumType.STRING)
    private PayStatus paymentStatus;
    private Date paidAt;

    // Default constructor
    public Payment() {
    }

    // Parameterized constructor
    public Payment(Long paymentId, Booking booking, Double amount,
                   Method paymentMethod, PayStatus paymentStatus, Date paidAt) {
        this.paymentId = paymentId;
        this.booking = booking;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
    }

    // Getters
    public Long getPaymentId() {
        return paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public Method getPaymentMethod() {
        return paymentMethod;
    }

    public PayStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    // Setters
    public void setPaymentId(Long paymentID) {
        this.paymentId = paymentID;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentMethod(Method paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(PayStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }
}