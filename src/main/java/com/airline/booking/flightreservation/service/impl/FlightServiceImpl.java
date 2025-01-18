package com.airline.booking.flightreservation.service.impl;

import com.airline.booking.flightreservation.model.Flight;
import com.airline.booking.flightreservation.model.Seat;
import com.airline.booking.flightreservation.repository.FlightRepository;
import com.airline.booking.flightreservation.repository.SeatRepository;
import com.airline.booking.flightreservation.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Uçuş bulunamadı!"));
    }

    @Override
    public Flight saveFlight(Flight flight) {
        // Flight bilgisini kaydet
        Flight savedFlight = flightRepository.save(flight);

        // 100 Koltuk oluştur ve kaydet
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("Seat " + i);
            seat.setStatus("AVAILABLE");
            seat.setFlight(savedFlight);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);

        return savedFlight;
    }

}
