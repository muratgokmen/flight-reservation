package com.airline.booking.flightreservation.config;

import com.airline.booking.flightreservation.service.UserService;
import com.airline.booking.flightreservation.utility.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    // Constructor-based injection (yeni ve tavsiye edilen yaklaşım)
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1) Authorization Header'ından token'ı alıyoruz
        final String authorizationHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 2) "Bearer " ile başlayan token'ı parse ediyoruz
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // "Bearer " kısmını çıkarıyoruz
            email = jwtTokenUtil.getEmailFromToken(token);
        }

        // 3) Elde ettiğimiz email, henüz Spring Security Context'e eklenmemişse doğrulamayı yapıyoruz
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4) Token geçerliyse userService üzerinden kullanıcı (UserDetails) bilgilerini alıyoruz
            if (jwtTokenUtil.validateToken(token)) {

                // loadUserByUsername metodu, email üzerinden user bilgisi döner
                // (UserService, UserDetailsService arayüzünü implemente ediyorsa)
                UserDetails userDetails = userService.loadUserByUsername(email);

                // 5) Authentication nesnesi oluşturuyoruz
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 6) Request detaylarını ekliyoruz (IP, session vs.)
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 7) SecurityContext'e Authentication'ı setliyoruz
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Filtre zincirinde ilerliyoruz
        filterChain.doFilter(request, response);
    }
}
