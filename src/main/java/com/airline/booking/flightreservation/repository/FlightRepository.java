package com.airline.booking.flightreservation.repository;

import com.airline.booking.flightreservation.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
