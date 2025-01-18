package com.airline.booking.flightreservation.repository;

import com.airline.booking.flightreservation.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightId(Long flightId);
}

