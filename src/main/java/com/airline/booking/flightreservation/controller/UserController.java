package com.airline.booking.flightreservation.controller;

import com.airline.booking.flightreservation.dto.AuthResponse;
import com.airline.booking.flightreservation.model.User;
import com.airline.booking.flightreservation.service.UserService;
import com.airline.booking.flightreservation.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthenticationManager myAuthenticationManager;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        userService.loginUser(user.getEmail(), user.getPassword());
        String token = jwtTokenUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(token);

       /* try {
            // 1) Authentication token nesnesi oluştur (Kullanıcıdan gelen email ve şifre ile)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            // 2) AuthenticationManager ile kimlik doğrulaması yap
            //    eğer başarısız olursa otomatik olarak Exception fırlatır (BadCredentialsException vs.)
            var authentication = myAuthenticationManager.authenticate(authToken);

            // 3) Auth başarılı ise, kullanıcı nesnesini veya email’ini al
            var principal = authentication.getPrincipal();
            // Genellikle principal, UserDetails tipinde olur.
            // Spring Security’de default “username” alanı, bizde “email”’e karşılık gelebilir.

            // Örnek: principal’ı UserDetails olarak cast edebiliriz
            // UserDetails userDetails = (UserDetails) principal;
            // String email = userDetails.getUsername();

            // 4) Token oluştur ve response olarak dön
            String generatedToken = jwtTokenUtil.generateToken(user.getEmail());

            // 5) JSON formatında token’ı gönderelim
            return ResponseEntity.ok(new AuthResponse(generatedToken));
        } catch (Exception e) {
            // Kimlik doğrulaması başarısız olduysa 401 HTTP Status dönelim
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }*/
    }

}
