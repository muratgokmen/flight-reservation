package com.airline.booking.flightreservation.service.impl;

import com.airline.booking.flightreservation.model.User;
import com.airline.booking.flightreservation.repository.UserRepository;
import com.airline.booking.flightreservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        // Şifreyi şifreleme
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Varsayılan rol
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        throw new RuntimeException("Geçersiz email veya şifre!");
    }

    /**
     * Spring Security tarafından çağrılır: username = email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1) Veritabanında ilgili email’e sahip kullanıcıyı bul
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // 2) UserDetails nesnesi döndür
        //    Basit şekilde Spring’in kendi UserDetails implementasyonunu kullanabiliriz:
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole()) // tek bir role varsa (ör. "USERs")
                .build();

        /*
         * Eğer rolü "ROLE_USER", "ROLE_ADMIN" gibi ön ekli tutuyorsanız:
         * .roles("ADMIN") -> Spring otomatik olarak "ROLE_ADMIN" olarak ekler.
         *
         * Çoklu roller varsa:
         * .authorities(new SimpleGrantedAuthority("ROLE_USER"),
         *              new SimpleGrantedAuthority("ROLE_ADMIN"))
         */
    }
}
