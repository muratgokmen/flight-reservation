package com.airline.booking.flightreservation.service;

import com.airline.booking.flightreservation.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User registerUser(User user);
    User loginUser(String email, String password);
    UserDetails loadUserByUsername(String email);
}