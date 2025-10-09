package com.zhumagulorken.cinema.showtime.controller;

import com.zhumagulorken.cinema.showtime.dto.UserDto;
import com.zhumagulorken.cinema.showtime.entity.User;
import com.zhumagulorken.cinema.showtime.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(
        name = "Users",
        description = "Operations related to user management"
)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all registered users."
    )
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Retrieve details of a specific user by their ID."
    )
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "User ID", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system."
    )
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Remove a user from the system by their ID."
    )
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", example = "1") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
