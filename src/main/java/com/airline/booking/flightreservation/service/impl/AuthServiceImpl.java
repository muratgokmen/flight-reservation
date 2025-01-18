package com.airline.booking.flightreservation.service.impl;

import com.airline.booking.flightreservation.dto.AuthRequest;
import com.airline.booking.flightreservation.model.User;
import com.airline.booking.flightreservation.utility.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil,
                           AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.customUserDetailService = customUserDetailService;
    }

    public String registerUser(AuthRequest user) {
        User newuser = new User();
        newuser.setUsername(user.username());
        newuser.setEmail(user.email());
        newuser.setPassword(passwordEncoder.encode(user.password()));
        newuser.setRole("USER"); // Varsayılan rol
        customUserDetailService.save(newuser);
        return "Kullanıcı oluşturuldu";
    }

    public String login(AuthRequest user) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.username(), user.password());
        authenticationManager.authenticate(authToken);
        return jwtTokenUtil.generateToken(user.username());
    }
}
