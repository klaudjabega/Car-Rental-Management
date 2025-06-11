package com.example.drivenimbus.service;

import com.example.drivenimbus.model.Car;
import com.example.drivenimbus.repository.BookingRepository;
import com.example.drivenimbus.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Fetch All
    public List<Car> fetchCarList() {
        return (List<Car>) carRepository.findAll();
    }

    // Fetch by ID
    public Car fetchCarById(Long carId) {
        return carRepository.findById(carId).orElse(null);
    }

    // Delete by ID
    public boolean deleteCarById(Long carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
            bookingRepository.deleteAllByCarId(carId);
            return true;
        }
        return false;
    }


    // Save Car
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    // Update Car
    public Car updateCar(Car updatedCar, Long carId) {
        return carRepository.findById(carId).map(car -> {
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setYear(updatedCar.getYear());
            car.setFuelType(updatedCar.getFuelType());
            car.setMileage(updatedCar.getMileage());
            car.setPrice(updatedCar.getPrice());
            car.setStatus(updatedCar.getStatus());
            car.setImageURL(updatedCar.getImageURL());
            car.setDescription(updatedCar.getDescription());
            return carRepository.save(car);
        }).orElse(null);
    }



}
