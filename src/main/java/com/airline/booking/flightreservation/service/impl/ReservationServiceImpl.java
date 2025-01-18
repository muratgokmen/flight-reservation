package com.airline.booking.flightreservation.service.impl;

import com.airline.booking.flightreservation.model.Reservation;
import com.airline.booking.flightreservation.model.Seat;
import com.airline.booking.flightreservation.repository.ReservationRepository;
import com.airline.booking.flightreservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation reserveSeat(Reservation reservation) {
        Seat seat = reservation.getSeat();
        /*if (!seat.getStatus().equals("AVAILABLE")) {
            throw new RuntimeException("Koltuk zaten rezerve edilmiş!");
        }*/

        seat.setStatus("RESERVED");
        reservation.setReservationTime(LocalDateTime.now());

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}
