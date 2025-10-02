package com.zhumagulorken.cinema.showtime.service;

import com.zhumagulorken.cinema.showtime.dto.UserDto;
import com.zhumagulorken.cinema.showtime.entity.User;
import com.zhumagulorken.cinema.showtime.enums.Role;
import com.zhumagulorken.cinema.showtime.exсeption.NotFoundException;
import com.zhumagulorken.cinema.showtime.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUsers() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setRole(Role.USER);

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> result = userService.getUsers();

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals(Role.USER, result.get(0).getRole());
    }

    @Test
    void testGetUserById_found() {
        User user = new User();
        user.setId(2L);
        user.setName("Bob");
        user.setEmail("bob@example.com");
        user.setRole(Role.ADMIN);

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserById(2L);

        assertEquals("Bob", result.getName());
        assertEquals(Role.ADMIN, result.getRole());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testCreateUser_success() {
        UserDto dto = new UserDto();
        dto.setName("Charlie");
        dto.setEmail("charlie@example.com");
        dto.setPassword("password123");
        dto.setRole(Role.USER);

        User savedUser = new User();
        savedUser.setId(3L);
        savedUser.setName("Charlie");
        savedUser.setEmail("charlie@example.com");
        savedUser.setPassword("password123");
        savedUser.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(dto);

        assertEquals(3L, result.getId());
        assertEquals("Charlie", result.getName());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    void testDeleteUser_success() {
        when(userRepository.existsById(4L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(4L);

        assertDoesNotThrow(() -> userService.deleteUser(4L));
        verify(userRepository, times(1)).deleteById(4L);
    }

    @Test
    void testDeleteUser_notFound() {
        when(userRepository.existsById(100L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> userService.deleteUser(100L));
    }
}
