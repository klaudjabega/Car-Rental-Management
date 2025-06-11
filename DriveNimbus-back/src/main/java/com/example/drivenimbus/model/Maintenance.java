package com.example.drivenimbus.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long maintenanceId;

    @ManyToOne
//    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private MaintanceStatus status;
    private String description;

    // Default constructor
    public Maintenance() {
    }

    // Constructor with all fields
    public Maintenance(Long maintenanceId, LocalDate startDate, LocalDate endDate, MaintanceStatus status, String description) {
        this.maintenanceId = maintenanceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(Long maintenanceID) {
        this.maintenanceId = maintenanceID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public MaintanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintanceStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }

}