package com.example.drivenimbus.controller;

import com.example.drivenimbus.model.Car;
import com.example.drivenimbus.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})

public class CarController {
    @Autowired 
    private CarService carService;

    @Order(1)
    @Operation(summary = "Get all cars")
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.fetchCarList());
    }

    @Order(2)
    @Operation(summary = "Get a specific car by ID")
    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        Car car = carService.fetchCarById(carId);
        return car != null ? ResponseEntity.ok(car) : ResponseEntity.notFound().build();
    }

    @Order(3)
    @Operation(summary = "Delete a car by ID // ADMIN")
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long carId) {
        if (carService.deleteCarById(carId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @Order(5)
    @Operation(summary = "Add a new car // ADMIN")
    @PostMapping
    public ResponseEntity<Car> saveCar(@Valid @RequestBody Car car) {
        return ResponseEntity.status(201).body(carService.saveCar(car));
    }

    @Order(4)
    @Operation(summary = "Update a car // ADMIN")
    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@RequestBody Car updatedCar, @PathVariable Long carId) {
        Car car = carService.updateCar(updatedCar, carId);
        return car != null ? ResponseEntity.ok(car) : ResponseEntity.notFound().build();
    }

}
