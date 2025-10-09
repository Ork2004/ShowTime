package com.zhumagulorken.cinema.showtime.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(
        name = "Admin",
        description = "Administrative panel endpoints"
)
public class AdminController {

    @GetMapping("/panel")
    @Operation(
            summary = "Access the admin panel",
            description = "Returns a welcome message for administrators. Requires admin role."
    )
    public ResponseEntity<String> adminPanel() {
        return ResponseEntity.ok("Welcome to Admin Panel!");
    }
}