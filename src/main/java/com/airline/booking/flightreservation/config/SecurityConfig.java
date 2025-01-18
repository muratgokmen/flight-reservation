package com.airline.booking.flightreservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // Constructor injection (tercih edilen yöntem)
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Spring Security'nin filtre zincirini yapılandırdığımız method.
     * WebSecurityConfigurerAdapter yerine bu yapıyı kullanıyoruz.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF korumasını kapatıyoruz (JWT tabanlı uygulamalarda çoğunlukla devre dışı bırakılır)
                .csrf(csrf -> csrf.disable())

                // Yetkilendirme kuralları
                .authorizeHttpRequests(auth -> auth
                        // Herkese açık endpoint'ler
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        // Diğer tüm endpoint'ler giriş yapmış kullanıcı gerektirir
                        .anyRequest().authenticated()
                )

                // Oturum yönetimini stateless olarak belirliyoruz (her istekte token doğrulanacak)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // JWT filtremizi UsernamePasswordAuthenticationFilter'dan önce ekliyoruz
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // HTTP yapılandırmasını tamamlayıp SecurityFilterChain bean'ini oluşturuyoruz
        return http.build();
    }

    /**
     * AuthenticationManager bean'i; eğer AuthenticationConfiguration üzerinden ihtiyacınız varsa bu şekilde alabilirsiniz.
     */
    @Bean
    public AuthenticationManager myAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
