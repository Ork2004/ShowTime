package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.UserDto;
import com.zhumagulorken.cinema.showtime.entity.User;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.UserRepository;
import com.zhumagulorken.cinema.showtime.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authentication",
        description = "User registration and login with JWT"
)
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/reqister")
    @Operation(
            summary = "Register a new user",
            description = "Create a new user account. The password is automatically hashed before saving."
    )
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticate a user by email and password, returning a JWT token if credentials are valid."
    )
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new NotFoundException(User.class, user.getEmail()));
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getRole());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
