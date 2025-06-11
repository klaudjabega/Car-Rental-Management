package com.example.drivenimbus.controller;

import com.example.drivenimbus.model.Booking;
import com.example.drivenimbus.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})

public class BookingController {

    @Autowired
    private BookingService bookingService;

//    HTTP Method | Endpoint | Purpose
//    POST | /bookings | Create a new booking
//    GET | /bookings | List all bookings (admin use)
//    GET | /bookings/{id} | Get a specific booking by ID
//    PUT | /bookings/{id} | Update a booking (e.g., reschedule)
//    DELETE | /bookings/{id} | Cancel/delete a booking

    @Order(1)
    @Operation(summary = "Get all bookings")
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.fetchAllBookings());
    }

    @Order(2)
    @Operation(summary = "Get a specific booking by ID")
    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.fetchBookingById(bookingId);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }


    @Order(5)
    @Operation(summary = "Cancel/delete a booking")
    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<?> deleteBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.fetchBookingById(bookingId);

        if (booking == null) {
            return ResponseEntity.notFound().build();
        }

        if (booking.getPickupDate().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().body("Booking is past the cancelation period");
        }

        boolean cancellationSuccessful = bookingService.cancelBooking(booking);
        return cancellationSuccessful ? ResponseEntity.ok().build() : ResponseEntity.status(400).body("Booking could not be cancelled");
    }

    @Order(6)
    @Operation(summary = "Update a booking (e.g., reschedule)")
    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @Validated @RequestBody Booking updatedBooking) {
        Booking booking = bookingService.updateBooking(updatedBooking, bookingId);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.notFound().build();
    }

//    HTTP Method | Endpoint | Purpose
//    GET | /users/{userId}/bookings | List bookings for a specific user
//    GET | /users/{userId}/bookings/upcoming | View only future bookings

    @Order(3)
    @Operation(summary = "List bookings for a specific userId")
    @GetMapping("/users/{userId}/bookings")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.fetchBookingsByUserId(userId);
        return bookings.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookings);
    }


    @Order(4)
    @Operation(summary = "View only future bookings of a specific userId")
    @GetMapping("/users/{userId}/bookings/upcoming")
    public ResponseEntity<List<Booking>> getBookingsByUserIdAndUpcoming(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.fetchBookingsByUserIdAndUpcoming(userId);
        return bookings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(bookings);
    }

    @Order(9)
    @Operation(summary = "Create a new booking for a specific userId")
    @PostMapping("/users/{userId}/bookings")
    public ResponseEntity<Booking> createBooking(@Validated @RequestBody Booking booking) {
        bookingService.saveBooking(booking);
        return ResponseEntity.status(201).body(booking);
    }

    @Order(7)
    @Operation(summary = "Approve/process a booking")
    @PutMapping("/bookings/{bookingId}/approve")
    public ResponseEntity<String> approveBooking(@PathVariable Long bookingId) {
        boolean approvalSuccess = bookingService.approveBooking(bookingId);
        return approvalSuccess ? ResponseEntity.status(201).body("Booking approved") : ResponseEntity.status(404).body("Booking not found or already processed");
    }

    @Order(8)
    @Operation(summary = "Reject/refuse a booking")
    @PutMapping("/bookings/{bookingId}/refuse")
    public ResponseEntity<String> refuseBooking(@PathVariable Long bookingId) {
        boolean rejectionSuccess = bookingService.rejectBooking(bookingId);
        return rejectionSuccess ? ResponseEntity.status(201).body("Booking rejected") : ResponseEntity.status(404).body("Booking not found or already processed");
    }


}
