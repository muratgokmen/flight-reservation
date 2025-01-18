package com.airline.booking.flightreservation.controller;

import com.airline.booking.flightreservation.model.Reservation;
import com.airline.booking.flightreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Koltuk Rezervasyonu Yap
    @PostMapping
    public Reservation reserveSeat(@RequestBody Reservation reservation) {
        return reservationService.reserveSeat(reservation);
    }

    // Kullanıcıya Ait Rezervasyonları Getir
    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUser(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }
}
