package com.example.drivenimbus.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;

    private String brand;
    private String model;
    private Integer year;

    @Enumerated(EnumType.STRING)
    private Fuel fuelType;
    private Integer mileage;
    private Double price;

    @Enumerated(EnumType.STRING)
    private State status;
    private String imageURL;
    private String description;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();

    // Default constructor
    public Car() {
    }

    // Parameterized constructor
    public Car(Long carId, String brand, String model, Integer year, Fuel fuelType,
               Integer mileage, Double price, State status, String imageURL, String description) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.price = price;
        this.status = status;
        this.imageURL = imageURL;
        this.description = description;
    }

    // Getters
    public Long getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public Fuel getFuelType() {
        return fuelType;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Double getPrice() {
        return price;
    }

    public State getStatus() {
        return status;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setCarId(Long carID) {
        this.carId = carID;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setFuelType(Fuel fuelType) {
        this.fuelType = fuelType;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(State status) {
        this.status = status;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}