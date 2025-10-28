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
                        // ===== PUBLIC ENDPOINTS =====
                        .requestMatchers(
                                "/", "/index.html", "/about.html",
                                "/login.html", "/register.html",
                                "/movies.html", "/my-tickets.html",
                                "/admin-dashboard.html", "/admin-movies.html",
                                "/admin-shows.html", "/admin-theaters.html",
                                "/admin-halls.html", "/admin-seats.html",
                                "/movie-details.html", "/show-selection.html",
                                "/seat-selection.html",
                                "/auth/**", "/css/**", "/js/**",
                                "/swagger-ui/**", "/v3/api-docs/**"
                        ).permitAll()

                        // ===== SHARED (USER + ADMIN) =====
                        .requestMatchers(HttpMethod.GET, "/movies/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/movies/*/shows/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/theaters/*/halls/*/seats/**").permitAll()

                        // ===== USER-ONLY ENDPOINTS =====
                        .requestMatchers("/users/*/tickets/**").hasRole("USER")

                        // ===== ADMIN-ONLY ENDPOINTS =====
                        .requestMatchers("/genres/**").hasRole("ADMIN")

                        // Movie management (Admin)
                        .requestMatchers(HttpMethod.POST, "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")

                        // Showtime management (Admin)
                        .requestMatchers(HttpMethod.POST, "/movies/*/shows/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movies/*/shows/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movies/*/shows/**").hasRole("ADMIN")

                        // Theater management (Admin)
                        .requestMatchers("/theaters/**").hasRole("ADMIN")

                        // Hall management (Admin)
                        .requestMatchers("/theaters/*/halls").hasRole("ADMIN")

                        // Seat management (Admin)
                        .requestMatchers(HttpMethod.POST, "/theaters/*/halls/*/seats/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/theaters/*/halls/*/seats/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/theaters/*/halls/*/seats/**").hasRole("ADMIN")

                        // ===== RESTRICTED ENDPOINTS =====
                        .requestMatchers("/users/**").denyAll()

                        // ===== ALL OTHER REQUESTS =====
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
