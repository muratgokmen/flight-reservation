package com.airline.booking.flightreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airline;

    private String departure;

    private String destination;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Double price;
}
