package com.airline.booking.flightreservation.utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Güvenli key oluşturma
    private final long expirationTime = 1000 * 60 * 60 * 10; // 10 saat

    // Token oluşturma
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey) // Güvenli key ile imzalama
                .compact();
    }

    // Token'dan email çıkarma
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Token doğrulama
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
