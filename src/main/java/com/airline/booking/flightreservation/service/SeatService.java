package com.airline.booking.flightreservation.service;

import com.airline.booking.flightreservation.model.Seat;

import java.util.List;

public interface SeatService {
    List<Seat> getSeatsByFlightId(Long flightId);
}
