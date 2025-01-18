package com.airline.booking.flightreservation.controller;

import com.airline.booking.flightreservation.dto.AuthRequest;
import com.airline.booking.flightreservation.dto.AuthResponse;
import com.airline.booking.flightreservation.model.User;
import com.airline.booking.flightreservation.service.impl.AuthServiceImpl;
import com.airline.booking.flightreservation.service.impl.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CustomUserDetailService userService;
    private final AuthServiceImpl authService;

    public UserController(CustomUserDetailService userService, AuthServiceImpl authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest user) {
        return new AuthResponse(authService.login(user));
    }

}
