package com.airline.booking.flightreservation.service;

import com.airline.booking.flightreservation.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation reserveSeat(Reservation reservation);
    List<Reservation> getReservationsByUserId(Long userId);
}
