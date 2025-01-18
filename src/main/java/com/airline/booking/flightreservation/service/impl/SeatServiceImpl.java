package com.airline.booking.flightreservation.service.impl;

import com.airline.booking.flightreservation.model.Seat;
import com.airline.booking.flightreservation.repository.SeatRepository;
import com.airline.booking.flightreservation.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<Seat> getSeatsByFlightId(Long flightId) {
        return seatRepository.findByFlightId(flightId);
    }
}
