package com.airline.booking.flightreservation.service;

import com.airline.booking.flightreservation.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();
    Flight getFlightById(Long id);
    Flight saveFlight(Flight flight);
}
