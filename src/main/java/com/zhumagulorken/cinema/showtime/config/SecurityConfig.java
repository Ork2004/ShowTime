package com.zhumagulorken.cinema.showtime.config;

import com.zhumagulorken.cinema.showtime.security.jwt.JwtAuthFilter;
import com.zhumagulorken.cinema.showtime.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (registration, login)
                        .requestMatchers("/auth/**").permitAll()

                        // USER-only endpoints
                        .requestMatchers("/tickets/**").hasRole("USER")

                        // ADMIN-only endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/movies/**").hasRole("ADMIN")
                        .requestMatchers("/shows/**").hasRole("ADMIN")
                        .requestMatchers("/halls/**").hasRole("ADMIN")
                        .requestMatchers("/seats/**").hasRole("ADMIN")

                        // Shared endpoints: both USER and ADMIN can access
                        .requestMatchers(HttpMethod.GET, "/movies/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/shows/**").hasAnyRole("USER", "ADMIN")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
