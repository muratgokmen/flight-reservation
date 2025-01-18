package com.airline.booking.flightreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // Örn: A1, B2

    private String status; // AVAILABLE, RESERVED

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}
