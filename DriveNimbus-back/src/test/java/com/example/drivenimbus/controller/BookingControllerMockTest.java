package com.example.drivenimbus.controller;

import com.example.drivenimbus.model.Booking;
import com.example.drivenimbus.model.Car;
import com.example.drivenimbus.model.Users;
import com.example.drivenimbus.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerMockTest {

    @MockitoBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //    HTTP Method | Endpoint | Purpose
    //POST | /bookings | Create a new booking
    //GET | /bookings | List all bookings (admin use)
    //GET | /bookings/{id} | Get a specific booking by ID
    //PUT | /bookings/{id} | Update a booking (e.g., reschedule)
    //DELETE | /bookings/{id} | Cancel/delete a booking

    private Booking booking;
    private Booking booking2;
    private Booking booking3;

    @BeforeEach
    void setUp() {
        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Yaris");
        car.setCarId(1L);

        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setModel("Civic");
        car2.setCarId(2L);

        Users user = new Users();
        user.setFullName("Chris");
        user.setUserId(1L);

        Users user2 = new Users();
        user2.setFullName("John");
        user2.setUserId(2L);

        booking = new Booking();
        booking.setCar(car);
        booking.setUser(user);
        booking.setBookingId(1L);
        booking.setPickupDate(LocalDate.of(2025, 5, 5));
        booking.setReturnDate(LocalDate.of(2025, 5, 10));

        booking2 = new Booking();
        booking2.setCar(car2);
        booking2.setUser(user);
        booking2.setBookingId(2L);
        booking2.setPickupDate(LocalDate.of(2025, 2, 15));
        booking2.setReturnDate(LocalDate.of(2025, 2, 20));

        booking3 = new Booking();
        booking3.setCar(car);
        booking3.setUser(user2);
        booking3.setBookingId(3L);
        booking3.setPickupDate(LocalDate.of(2025, 5, 25));
        booking3.setReturnDate(LocalDate.of(2025, 5, 30));


    }

    // Return 200 when Booking is found
    @Test
    void shouldReturnBookingByUsingId() throws Exception {

        Mockito.when(bookingService.fetchBookingById(1L)).thenReturn(booking);

        // Perform GET request on the /bookings/{id} endpoint
        mockMvc.perform(get("/bookings/{id}", 1L))
                .andExpect(status().isOk())  // Expect HTTP status 200 OK
                .andExpect(jsonPath("$.bookingID").value(1L))  // Check BookingID
                .andExpect(jsonPath("$.car.brand").value("Toyota"))  // Check car brand
                .andExpect(jsonPath("$.user.fullName").value("Chris"))  // Check user full name
                .andExpect(jsonPath("$.pickupDate").value("2025-05-05"))  // Check pickup date
                .andExpect(jsonPath("$.returnDate").value("2025-05-10"));  // Check return dat
    }

    @Test
    void shouldSaveBooking() throws Exception {
        Mockito.when(bookingService.saveBooking(booking)).thenReturn(booking);

        mockMvc.perform(post("/bookings").contentType("application/json").content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingID").value(1L))
                .andExpect(jsonPath("$.car.brand").value("Toyota"))
                .andExpect(jsonPath("$.user.fullName").value("Chris"))
                .andExpect(jsonPath("$.pickupDate").value("2025-05-05"))
                .andExpect(jsonPath("$.returnDate").value("2025-05-10"));
    }

    @Test
    void shouldDeleteBooking() throws Exception {
        Long bookingId = 1L;

        Mockito.when(bookingService.cancelBooking(booking)).thenReturn(true);
        mockMvc.perform(delete("/bookings/{id}", bookingId))
                .andExpect(status().isOk());

        Mockito.verify(bookingService, Mockito.times(1)).cancelBooking(booking);
    }

    @Test
    void shouldUpdateBooking() throws Exception {
        Long bookingId = 1L;
        Booking updatedBooking = new Booking();
        updatedBooking.setBookingId(1L);
        updatedBooking.setPickupDate(LocalDate.of(2025, 5, 15));
        updatedBooking.setReturnDate(LocalDate.of(2025, 5, 20));
        Mockito.when(bookingService.updateBooking(any(Booking.class), eq(bookingId))).thenReturn(updatedBooking);

        mockMvc.perform(put("/bookings/{id}", bookingId).contentType("application/json").content(objectMapper.writeValueAsString(updatedBooking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingID").value(1L))
                .andExpect(jsonPath("$.PickupDate").value("2025-05-15"))
                .andExpect(jsonPath("$.ReturnDate").value("2025-05-20"));
    }

    //    HTTP Method | Endpoint | Purpose
//    GET | /users/{userId}/bookings | List bookings for a specific user
//    POST | /users/{userId}/bookings | Create a booking for a specific user
//    GET | /users/{userId}/bookings/upcoming | View only future bookings

    @Test
    void shouldReturnBookingsByUserId() throws Exception {
        Long userId = 1L;
        List<Booking> bookings = List.of(booking,booking2);

        Mockito.when(bookingService.fetchBookingsByUserId(userId)).thenReturn(bookings);

        mockMvc.perform(get("/users/{userId}/bookings", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].car.brand").value("Toyota"))
                .andExpect(jsonPath("$[1].car.brand").value("Honda"));
    }

    @Test
    void shouldReturnBookingsByUserIdAndUpcoming() throws Exception {
        Long userId = 1L;
        List<Booking> bookings = List.of(booking);

        Mockito.when(bookingService.fetchBookingsByUserIdAndUpcoming(userId)).thenReturn(bookings);

        mockMvc.perform(get("/users/{userId}/bookings/upcoming", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].car.brand").value("Toyota"));
    }
}
