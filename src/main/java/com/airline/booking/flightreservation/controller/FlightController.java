package com.airline.booking.flightreservation.controller;

import com.airline.booking.flightreservation.model.Flight;
import com.airline.booking.flightreservation.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @PostMapping
    public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightService.saveFlight(flight);
        return ResponseEntity.ok(savedFlight);
    }
}
