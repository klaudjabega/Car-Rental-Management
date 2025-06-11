package com.example.drivenimbus.controller;

import com.example.drivenimbus.model.Car;
import com.example.drivenimbus.model.Fuel;
import com.example.drivenimbus.model.State;
import com.example.drivenimbus.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnListOfCars() throws Exception {
        List<Car> cars = List.of(
                new Car(1L, "Toyota", "Corolla", 2020, Fuel.GASOLINE, 30000, 75.00, State.AVAILABLE, "", ""),
                new Car(2L, "Tesla", "Model 3", 2022, Fuel.ELECTRIC, 15000, 120.00, State.AVAILABLE, "", "")
        );

        Mockito.when(carService.fetchCarList()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].brand").value("Toyota"));
    }

    @Test
    void shouldAddCar() throws Exception {
        Car car = new Car(3L, "Honda", "Civic", 2019, Fuel.GASOLINE, 40000, 65.0, State.AVAILABLE, "", "");

        Mockito.when(carService.saveCar(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.model").value("Civic"));
    }

    @Test
    void shouldDeleteCar() throws Exception {
        Long carId = 1L;
        mockMvc.perform(delete("/cars/{id}", carId))
                .andExpect(status().isOk());

        Mockito.verify(carService).deleteCarById(carId);

    }


}
