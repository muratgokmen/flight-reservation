package com.airline.booking.flightreservation.controller;

import com.airline.booking.flightreservation.model.Seat;
import com.airline.booking.flightreservation.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/flight/{flightId}")
    public List<Seat> getSeatsByFlight(@PathVariable Long flightId) {
        return seatService.getSeatsByFlightId(flightId);
    }
}
