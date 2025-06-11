package com.example.drivenimbus.repository;

import com.example.drivenimbus.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserUserId(Long userId);
    List<Booking> findByUserUserIdAndPickupDateAfter(Long userId, LocalDate today);
    @Modifying
    @Query("DELETE FROM Booking b WHERE b.car.id = :carId")
    void deleteAllByCarId(@Param("carId") Long carId);
}
